package com.alexeyyuditsky.exchange_rates

import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.exchange_rates.model.Currency
import com.alexeyyuditsky.exchange_rates.utils.currentRubAgainstDollar
import com.alexeyyuditsky.exchange_rates.utils.yesterdayRubAgainstDollar
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MainViewModel : ViewModel() {

    val yesterdayCurrencyList = mutableListOf<Currency>()
    val currentCurrencyList = mutableListOf<Currency>()

    fun initCurrencies() {
        repeat(currentCurrencyList.size) {
            // разница валют (сегодня, вчера)
            currentCurrencyList[it].yesterdayValue =
                (currentRubAgainstDollar / currentCurrencyList[it].value) - (yesterdayRubAgainstDollar / yesterdayCurrencyList[it].value)
            // нахождения рубля из доллара
            currentCurrencyList[it].value = (currentRubAgainstDollar / currentCurrencyList[it].value)
        }

        _currentCurrencyListFlow.tryEmit(currentCurrencyList)
    }

    private val _currentCurrencyListFlow = MutableSharedFlow<List<Currency>>(extraBufferCapacity = 1)
    val currentCurrencyListFlow = _currentCurrencyListFlow.asSharedFlow()
}