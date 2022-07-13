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

class AllCurrenciesViewModel : ViewModel() {

    private val _currentCurrencyListFlow = MutableSharedFlow<List<Currency>>(1)
    val currentCurrencyListFlow = _currentCurrencyListFlow.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _currentCurrencyListFlow.emit(Singletons.retrofitApi.getCurrencies(currentDate).currencies)
        }
    }

}

