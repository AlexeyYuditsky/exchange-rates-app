package com.alexeyyuditsky.exchange_rates.screens.all_currencies

import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.exchange_rates.model.Currency
import com.alexeyyuditsky.exchange_rates.utils.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AllCurrenciesViewModel : ViewModel() {

    val yesterdayCurrencyList = mutableListOf<Currency>()
    val currentCurrencyList = mutableListOf<Currency>()

    private val _currentCurrencyListFlow = MutableSharedFlow<List<Currency>>(extraBufferCapacity = 1)
    val currentCurrencyListFlow = _currentCurrencyListFlow.asSharedFlow()

    fun initCurrencies() {
        repeat(currentCurrencyList.size) {
            currentCurrencyList[it].yesterdayValue = getCurrencyChangeRelativeToTodayAndYesterday(
                currentCurrencyList[it].value,
                yesterdayCurrencyList[it].value
            )
            currentCurrencyList[it].value = getRubleValueFromCurrency(currentCurrencyList[it].value)
        }
        _currentCurrencyListFlow.tryEmit(currentCurrencyList)
    }

}