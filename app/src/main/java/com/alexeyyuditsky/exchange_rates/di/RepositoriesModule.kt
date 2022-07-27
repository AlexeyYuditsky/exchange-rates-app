package com.alexeyyuditsky.exchange_rates.di

import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.CurrenciesRepository
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room.RoomCurrenciesRepository
import com.alexeyyuditsky.exchange_rates.network.CurrenciesSource
import com.alexeyyuditsky.exchange_rates.network.RetrofitCurrenciesSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindCurrenciesRepository(
        roomCurrenciesRepository: RoomCurrenciesRepository
    ): CurrenciesRepository

}