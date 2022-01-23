package com.example.myweatherforecastapp.ui.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.core.Constants.DEFAULT_UNIT
import com.example.core.model.ResultState
import com.example.domain.model.CityEntity
import com.example.domain.model.SettingModel
import com.example.domain.model.WeatherModel
import com.example.domain.model.request.WeatherRequestModel
import com.example.domain.usecase.*
import com.example.myweatherforecastapp.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class SearchCityViewModel(
    private val getCityListByNameUseCase: GetCityListByNameUseCase,
    private val saveAppSettingUseCase: SaveAppSettingUseCase,
    private val getAppSettingUseCase: GetAppSettingUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val loadCitiesUseCase: LoadCitiesUseCase
) : BaseViewModel() {

    private var _searchPagingData = MutableStateFlow<PagingData<CityEntity>>(PagingData.empty())
    val searchPagingData get() = _searchPagingData.asStateFlow()

    private val _showFullPageLoading = MutableLiveData<Boolean>()
    val showFullPageLoading: LiveData<Boolean> get() = _showFullPageLoading

    private val _saveSettingResponse = MutableLiveData<ResultState<WeatherModel>>()
    val saveSettingResponse: LiveData<ResultState<WeatherModel>> get() = _saveSettingResponse

    val appSettingModel: StateFlow<SettingModel> = getAppSettingUseCase.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingModel()
        )

    fun setupLoadAllCities(){
        _showFullPageLoading.value = true

        viewModelScope.launch {
            loadCitiesUseCase.execute()
                .collectLatest {
                    _showFullPageLoading.value = false
                }
        }
    }

    fun searchCity(cityName: String) {

        if (cityName.isEmpty()) return

        viewModelScope.launch {

            getCityListByNameUseCase.execute(cityName = cityName)
                .collectLatest { citySearchResult ->
                    _searchPagingData.value = citySearchResult
                }

        }

    }

    fun loadWeatherForUpdateLatLonSetting(cityName: String) {
        val unit = if (appSettingModel.value.unit.isEmpty()) {
            DEFAULT_UNIT
        } else {
            appSettingModel.value.unit
        }

        viewModelScope.launch {

            _saveSettingResponse.value = ResultState.Loading

            getWeatherUseCase.execute(
                weatherRequestModel = getWeatherRequest(
                    unit = unit,
                    cityName = cityName.lowercase(Locale.ROOT)
                )
            ).onEach {
                if (it is ResultState.Success) {
                    if (it.data.cityName.isNotEmpty() && it.data.lat.isNotEmpty() && it.data.lon.isNotEmpty()) {
                        saveAppSetting(
                            cityName = cityName,
                            lat = it.data.lat,
                            lon = it.data.lon,
                            unit = unit
                        )
                    }
                }
            }.flowOn(Dispatchers.IO)
                .collectLatest {
                    _saveSettingResponse.value = it
                }
        }
    }

    private fun getWeatherRequest(unit: String, cityName: String): WeatherRequestModel {
        return WeatherRequestModel(
            query = cityName,
            units = unit
        )
    }

    private suspend fun saveAppSetting(cityName: String, lat: String, lon: String, unit: String) {
        saveAppSettingUseCase.execute(
            unit = unit,
            cityName = cityName,
            lat = lat,
            lon = lon
        )
    }

}