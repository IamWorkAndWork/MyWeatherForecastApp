package com.example.data.providers

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.core.Constants
import com.example.data.remote.api.CountriesNowAPI
import com.example.data.remote.api.OpenWeatherMapService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


fun provideLoggingInterceptoe(): HttpLoggingInterceptor {
    val interceltor = HttpLoggingInterceptor()
    interceltor.level = HttpLoggingInterceptor.Level.BODY
    return interceltor
}

fun provideHttpClientWeatherService(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    chuckerInterceptor: ChuckerInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(chuckerInterceptor)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .setApiKey()
        .build()
}

fun provideHttpClientCountryService(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    chuckerInterceptor: ChuckerInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(chuckerInterceptor)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
}

fun provideConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create()
}

fun provideRetrofitCountryInstance(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_COUNTRIES_NOW)
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()
}

fun provideRetrofitOpenWeatherInstance(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_OPEN_WEATHER_MAP)
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()
}

fun OkHttpClient.Builder.setApiKey(): OkHttpClient.Builder = addInterceptor { chain ->
    val original = chain.request()

    val originalHttpUrl = original.url
    val url = originalHttpUrl.newBuilder()
        .addQueryParameter(Constants.APP_ID, Constants.API_KEY)
        .build()

    val requestBuilder = original.newBuilder()
        .header("Accept", "application/json")
        .url(url)

    val request = requestBuilder.build()
    chain.proceed(request)
}

fun provideCountryNowService(retrofit: Retrofit): CountriesNowAPI {
    return retrofit.create(CountriesNowAPI::class.java)
}

fun provideOpenWeatherMapService(retrofit: Retrofit): OpenWeatherMapService {
    return retrofit.create(OpenWeatherMapService::class.java)
}