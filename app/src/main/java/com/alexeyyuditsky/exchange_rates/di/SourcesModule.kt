package com.alexeyyuditsky.exchange_rates.di

import com.alexeyyuditsky.exchange_rates.network.CurrenciesSource
import com.alexeyyuditsky.exchange_rates.network.RetrofitCurrenciesSource
import com.alexeyyuditsky.exchange_rates.room.CurrenciesDao
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourcesModule {

    @Binds
    abstract fun bindCurrenciesSource(
        retrofitCurrenciesSource: RetrofitCurrenciesSource
    ): CurrenciesSource

}