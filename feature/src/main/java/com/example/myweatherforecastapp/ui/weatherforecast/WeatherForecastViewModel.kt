package com.example.myweatherforecastapp.ui.weatherforecast

import androidx.lifecycle.viewModelScope
import com.example.core.ext.toDayDateTimeWithFormat
import com.example.core.model.DATE_TIME_FORMAT_d_MMM_yyyy
import com.example.core.model.DATE_TIME_FORMAT_dd_MM_yyyy
import com.example.core.model.ResultState
import com.example.core.model.map
import com.example.domain.model.ForecastModel
import com.example.domain.model.SettingModel
import com.example.domain.usecase.GetAppSettingUseCase
import com.example.domain.usecase.GetForecastUseCase
import com.example.domain.usecase.SaveAppSettingUseCase
import com.example.domain.usecase.forecast.GetTodayForecastUseCase
import com.example.myweatherforecastapp.model.ForecastUiModel
import com.example.myweatherforecastapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WeatherForecastViewModel(
    private val getTodayForecastUseCase: GetTodayForecastUseCase,
    private val getAppSettingUseCase: GetAppSettingUseCase,
) : BaseViewModel() {

    private val _forecastUiModel =
        MutableStateFlow<ResultState<ForecastUiModel>>(ResultState.Loading)
    val forecastUiModel get() = _forecastUiModel.asStateFlow()

    val appSettingModel: StateFlow<SettingModel> = getAppSettingUseCase.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingModel()
        )

    fun loadWeatherForecastUiData() {

        val setting = appSettingModel.value

        if (setting.cityName.isEmpty() || setting.unit.isEmpty()) return

        viewModelScope.launch {

            getTodayForecastUseCase.execute(settingModel = setting)
                .map(::mapToUiModel)
                .collectLatest { result ->
                    _forecastUiModel.value = result
                }

        }

    }

    private fun mapToUiModel(result: ResultState<ForecastModel>) =
        result.map { model ->
            ForecastUiModel(
                forecastModel = model,
                cityName = appSettingModel.value.cityName,
                todayDate = toDayDateTimeWithFormat(DATE_TIME_FORMAT_d_MMM_yyyy)
            )
        }

}