package com.alexeyyuditsky.exchange_rates.screens.all_currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchange_rates.Singletons
import com.alexeyyuditsky.exchange_rates.network.Currency
import com.alexeyyuditsky.exchange_rates.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AllCurrenciesViewModel : ViewModel() {

    private val _currentCurrencyListFlow = MutableSharedFlow<List<Currency>>(1)
    val currentCurrencyListFlow = _currentCurrencyListFlow.asSharedFlow()

    private val _currencyDateFlow = MutableSharedFlow<String>(1)
    val currencyDateFlow = _currencyDateFlow.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val convertedRoot = Singletons.retrofitApi.getCurrencies(currentDate())
                _currentCurrencyListFlow.emit(convertedRoot.currencies)
                _currencyDateFlow.emit(convertedRoot.date)
            } catch (e: HttpException) {
                val convertedRoot = Singletons.retrofitApi.getCurrencies(yesterdayDate())
                _currentCurrencyListFlow.emit(convertedRoot.currencies)
                _currencyDateFlow.emit(convertedRoot.date)
            }
        }
    }

}

