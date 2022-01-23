package com.example.app

import android.app.Application
import androidx.multidex.MultiDex
import com.example.di.dataLayerModule
import com.example.di.domainLayerModules
import com.example.myweatherforecastapp.di.presentationLayerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

class WeatherForecastApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@WeatherForecastApplication)
            modules(modules)
        }
    }

}

val modules = mutableListOf<Module>().apply {
    addAll(dataLayerModule)
    addAll(domainLayerModules)
    addAll(presentationLayerModule)
}