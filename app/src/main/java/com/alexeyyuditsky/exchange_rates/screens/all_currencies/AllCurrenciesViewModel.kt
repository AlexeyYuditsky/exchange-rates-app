package com.alexeyyuditsky.exchange_rates.screens.all_currencies

import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.exchange_rates.network.Currency
import com.alexeyyuditsky.exchange_rates.utils.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

val yesterdayCurrenciesList = mutableListOf<Currency>()

class AllCurrenciesViewModel : ViewModel() {

    val currentCurrenciesList = mutableListOf<Currency>()

    private val _currentCurrencyListFlow = MutableSharedFlow<List<Currency>>(extraBufferCapacity = 1)
    val currentCurrencyListFlow = _currentCurrencyListFlow.asSharedFlow()

    fun initCurrencies() = _currentCurrencyListFlow.tryEmit(currentCurrenciesList)

}

