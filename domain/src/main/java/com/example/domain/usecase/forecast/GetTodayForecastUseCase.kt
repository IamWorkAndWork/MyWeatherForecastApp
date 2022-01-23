package com.example.domain.usecase.forecast

import com.example.core.ext.unixDateToDateTimeString
import com.example.core.model.DATE_TIME_FORMAT_yyyy_mm_dd
import com.example.core.model.ResultState
import com.example.core.model.map
import com.example.domain.model.ForecastModel
import com.example.domain.model.HourlyModel
import com.example.domain.model.SettingModel
import com.example.domain.model.request.ForecastRequestModel
import com.example.domain.model.request.TimeMachineForecastRequestModel
import com.example.domain.usecase.GetForecastUseCase
import com.example.domain.usecase.GetTimeMachineForecastUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.*

interface GetTodayForecastUseCase {
    fun execute(settingModel: SettingModel): Flow<ResultState<ForecastModel>>
}

class GetTodayForecastUseCaseImpl(
    private val getForecastUseCase: GetForecastUseCase,
    private val getTimeMachineForecastUseCase: GetTimeMachineForecastUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetTodayForecastUseCase {

    override fun execute(settingModel: SettingModel): Flow<ResultState<ForecastModel>> {

        val today = Calendar.getInstance().time
        val format = SimpleDateFormat(DATE_TIME_FORMAT_yyyy_mm_dd)
        val dateCurrent = format.format(today)

        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_MONTH, -1)

        return getForecastUseCase.execute(request = getForecastRequestModel(setting = settingModel))
            .combine(
                getTimeMachineForecastUseCase.execute(
                    request = getTimeMachineRequestModel(
                        setting = settingModel,
                        datetime = (System.currentTimeMillis() / 1000).toString()
                    )
                )
            ) { forecastA: ResultState<ForecastModel>, forecastB: ResultState<ForecastModel> ->
                filterHourlyOnlyForToday(forecastA, forecastB, dateCurrent)
            }
            .combine(
                getTimeMachineForecastUseCase.execute(
                    request = getTimeMachineRequestModel(
                        setting = settingModel,
                        datetime = (yesterday.time.time / 1000).toString()
                    )
                )
            ) { forecastA: ResultState<ForecastModel>, forecastB: ResultState<ForecastModel> ->
                filterHourlyOnlyForToday(forecastA, forecastB, dateCurrent)
            }
            .catch {
                emit(ResultState.Error(it))
            }
            .flowOn(dispatcher)

    }

    private fun filterHourlyOnlyForToday(
        forecastA: ResultState<ForecastModel>,
        forecastB: ResultState<ForecastModel>,
        dateCurrent: String?
    ): ResultState<ForecastModel> {

        checkException(forecastA, forecastB)

        val hourlyModelList = mutableListOf<HourlyModel>()

        forecastA.map {
            it.hourlyModelList.forEach {
                hourlyModelList.add(it)
            }
        }

        forecastB.map {
            it.hourlyModelList.forEach {
                hourlyModelList.add(it)
            }
        }

        return forecastA.map {
            it.copy(
                hourlyModelList = hourlyModelList
                    .filter { hourly ->
                        val date = hourly.dateTime?.unixDateToDateTimeString(
                            DATE_TIME_FORMAT_yyyy_mm_dd
                        )
                        dateCurrent == date
                    }
                    .distinctBy {
                        it.dateTime
                    }
                    .sortedBy {
                        it.dateTime
                    }
            )
        }
    }

    private fun checkException(
        forecastA: ResultState<ForecastModel>,
        forecastB: ResultState<ForecastModel>
    ) {
        if (forecastA is ResultState.Error) {
            throw forecastA.exception
        }
        if (forecastB is ResultState.Error) {
            throw forecastB.exception
        }
    }

    private fun getTimeMachineRequestModel(setting: SettingModel, datetime: String) =
        TimeMachineForecastRequestModel(
            lat = setting.lat,
            lon = setting.lon,
            units = setting.unit,
            dateTime = datetime
        )

    private fun getForecastRequestModel(setting: SettingModel) =
        ForecastRequestModel(
            lat = setting.lat,
            lon = setting.lon,
            units = setting.unit
        )

}