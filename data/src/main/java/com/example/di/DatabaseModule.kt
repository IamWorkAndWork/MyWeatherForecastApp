package com.example.di

import com.example.data.local.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        AppDatabase.getDatabase(context = androidContext())
    }

}