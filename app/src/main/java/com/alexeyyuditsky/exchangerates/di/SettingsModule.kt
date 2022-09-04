package com.alexeyyuditsky.exchangerates.di

import com.alexeyyuditsky.exchangerates.model.settings.AppSettings
import com.alexeyyuditsky.exchangerates.model.settings.SharedPreferencesAppSettings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    abstract fun bindAppSettings(
        appSettings: SharedPreferencesAppSettings
    ): AppSettings

}
