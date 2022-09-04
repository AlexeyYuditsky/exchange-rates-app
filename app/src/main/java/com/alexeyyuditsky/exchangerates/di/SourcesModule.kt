package com.alexeyyuditsky.exchangerates.di

import com.alexeyyuditsky.exchangerates.model.network.CurrenciesSource
import com.alexeyyuditsky.exchangerates.model.network.RetrofitCurrenciesSource
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