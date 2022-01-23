package com.example.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.core.Constants.COUNTRY_INSTANCE
import com.example.core.Constants.OPEN_WEATHER_MAP_INSTANCE
import com.example.data.remote.api.CountriesNowAPI
import com.example.data.remote.api.OpenWeatherMapService
import com.example.data.providers.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networModule = module {

    single<ChuckerInterceptor> {
        val chuckerCollector = ChuckerCollector(
            context = androidContext(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        ChuckerInterceptor.Builder(context = androidContext())
            .collector(chuckerCollector)
            .build()
    }

    single<HttpLoggingInterceptor> {
        provideLoggingInterceptoe()
    }

    single<OkHttpClient>(named(OPEN_WEATHER_MAP_INSTANCE)) {
        provideHttpClientWeatherService(
            httpLoggingInterceptor = get(),
            chuckerInterceptor = get()
        )
    }

    single<OkHttpClient>(named(COUNTRY_INSTANCE)) {
        provideHttpClientCountryService(
            httpLoggingInterceptor = get(),
            chuckerInterceptor = get()
        )
    }

    single<GsonConverterFactory> {
        provideConverterFactory()
    }

    single<Retrofit>(named(COUNTRY_INSTANCE)) {
        provideRetrofitCountryInstance(
            okHttpClient = get(named(OPEN_WEATHER_MAP_INSTANCE)),
            gsonConverterFactory = get()
        )
    }

    single<Retrofit>(named(OPEN_WEATHER_MAP_INSTANCE)) {
        provideRetrofitOpenWeatherInstance(
            okHttpClient = get(named(OPEN_WEATHER_MAP_INSTANCE)),
            gsonConverterFactory = get()
        )
    }

    single<CountriesNowAPI> {
        provideCountryNowService(
            retrofit = get(named(COUNTRY_INSTANCE))
        )
    }

    single<OpenWeatherMapService> {
        provideOpenWeatherMapService(
            retrofit = get(named(OPEN_WEATHER_MAP_INSTANCE))
        )
    }

}