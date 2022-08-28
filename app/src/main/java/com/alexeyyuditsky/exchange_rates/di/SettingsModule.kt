package com.alexeyyuditsky.exchange_rates.di

import com.alexeyyuditsky.exchange_rates.model.settings.AppSettings
import com.alexeyyuditsky.exchange_rates.model.settings.SharedPreferencesAppSettings
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
