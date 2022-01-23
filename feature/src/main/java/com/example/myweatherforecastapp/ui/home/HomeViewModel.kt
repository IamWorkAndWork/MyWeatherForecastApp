package com.example.myweatherforecastapp.ui.home

import androidx.lifecycle.viewModelScope
import com.example.core.model.ResultState
import com.example.domain.model.ForecastModel
import com.example.domain.model.SettingModel
import com.example.domain.model.WeatherModel
import com.example.domain.model.request.ForecastRequestModel
import com.example.domain.model.request.WeatherRequestModel
import com.example.domain.usecase.*
import com.example.myweatherforecastapp.mapper.ResultToHomeUiModelMapper
import com.example.myweatherforecastapp.model.HomeUiModel
import com.example.myweatherforecastapp.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HomeViewModel(
    private val getAppSettingUseCase: GetAppSettingUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val resultToHomeUiModelMapper: ResultToHomeUiModelMapper
) : BaseViewModel() {

    private val _homeUiModel = MutableStateFlow<ResultState<List<HomeUiModel>>>(ResultState.Loading)
    val homeUiModel get() = _homeUiModel.asStateFlow()

    val appSettingModel: StateFlow<SettingModel> = getAppSettingUseCase.execute()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = SettingModel()
        )

    fun loadHomeUiData() {

        val setting = appSettingModel.value

        if (setting.cityName.isEmpty() || setting.unit.isEmpty()) return

        viewModelScope.launch {

            getWeatherUseCase.execute(
                weatherRequestModel = getWeatherRequest(setting = setting)
            ).combine(
                /*
                 * this is call for forecast API; for example when we would like to implement 7-day forecast
                 */
                getForecastUseCase.execute(getForecastRequest(setting = setting))
            ) { weatherModel: ResultState<WeatherModel>, forecastModel: ResultState<ForecastModel> ->
                /*
                 * use resultToHomeUiModelMapper mapper to map foe UI Model which position we want to render
                 */
                resultToHomeUiModelMapper.transform(weatherModel, forecastModel)
            }.catch {
                _homeUiModel.value = ResultState.Error(it)
            }.collectLatest { homeUiModel: ResultState<List<HomeUiModel>> ->
                _homeUiModel.value = homeUiModel
            }

        }

    }

    private fun getForecastRequest(setting: SettingModel): ForecastRequestModel {
        return ForecastRequestModel(
            lat = setting.lat,
            lon = setting.lon,
            units = setting.unit
        )
    }

    private fun getWeatherRequest(setting: SettingModel): WeatherRequestModel {
        return WeatherRequestModel(
            query = setting.cityName,
            units = setting.unit
        )
    }

}