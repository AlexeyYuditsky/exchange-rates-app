package com.alexeyyuditsky.exchangerates.di

import com.alexeyyuditsky.exchangerates.model.currencies.repositories.CurrenciesRepository
import com.alexeyyuditsky.exchangerates.model.currencies.repositories.room.RoomCurrenciesRepository
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