package com.example.myweatherforecastapp.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SettingModel
import com.example.domain.usecase.GetAppSettingUseCase
import com.example.domain.usecase.SaveAppSettingUseCase
import com.example.myweatherforecastapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getAppSettingUseCase: GetAppSettingUseCase,
    private val saveAppSettingUseCase: SaveAppSettingUseCase
) : BaseViewModel() {

    val appSettingModel: StateFlow<SettingModel> = getAppSettingUseCase.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingModel()
        )

    fun saveAppSetting(unit: String) {

        val appSetting = appSettingModel.value

        viewModelScope.launch {
            saveAppSettingUseCase.execute(
                unit = unit,
                cityName = appSetting.cityName,
                lat = appSetting.lat,
                lon = appSetting.lon
            )
        }
    }

}