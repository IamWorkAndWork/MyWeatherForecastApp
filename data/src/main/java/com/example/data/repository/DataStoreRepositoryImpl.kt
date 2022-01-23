package com.example.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.Constants.DEFAULT_CITY_NAME
import com.example.core.Constants.DEFAULT_LAT
import com.example.core.Constants.DEFAULT_LON
import com.example.core.Constants.DEFAULT_UNIT
import com.example.core.Constants.PREFERENCES_CITY_NAME
import com.example.core.Constants.PREFERENCES_LAT
import com.example.core.Constants.PREFERENCES_LON
import com.example.core.Constants.PREFERENCES_NAME
import com.example.core.Constants.PREFERENCES_UNIT
import com.example.domain.model.SettingModel
import com.example.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreRepositoryImpl(
    context: Context
) : DataStoreRepository {

    private object PreferenceKeys {
        val selectedCityName = stringPreferencesKey(PREFERENCES_CITY_NAME)
        val selectedUnit = stringPreferencesKey(PREFERENCES_UNIT)
        val selectedLat = stringPreferencesKey(PREFERENCES_LAT)
        val selectedLon = stringPreferencesKey(PREFERENCES_LON)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    override suspend fun saveAppSetting(
        unit: String,
        cityName: String,
        lat: String,
        lon: String
    ) {

        dataStore.edit { preferences ->
            preferences.set(PreferenceKeys.selectedUnit, unit)
            preferences.set(PreferenceKeys.selectedCityName, cityName)
            preferences.set(PreferenceKeys.selectedLat, lat)
            preferences.set(PreferenceKeys.selectedLon, lon)
        }

    }

    override fun getSetting(): Flow<SettingModel> {

        return dataStore.data.catch { error ->

            if (error is IOException) {
                emit(emptyPreferences())
            } else {
                throw error
            }

        }.map { preferences ->

            val selectedUnit = preferences[PreferenceKeys.selectedUnit] ?: DEFAULT_UNIT
            val selectedCityName = preferences[PreferenceKeys.selectedCityName] ?: DEFAULT_CITY_NAME
            val selectedLat = preferences[PreferenceKeys.selectedLat] ?: DEFAULT_LAT
            val selectedLon = preferences[PreferenceKeys.selectedLon] ?: DEFAULT_LON

            SettingModel(
                cityName = selectedCityName,
                unit = selectedUnit,
                lat = selectedLat,
                lon = selectedLon
            )

        }

    }

}