package com.alexeyyuditsky.exchange_rates.screens.all_currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchange_rates.network.CurrenciesSource
import com.alexeyyuditsky.exchange_rates.network.Currency
import com.alexeyyuditsky.exchange_rates.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AllCurrenciesViewModel @Inject constructor(
    private val currenciesSource: CurrenciesSource
) : ViewModel() {

    private val _currentCurrencyListFlow = MutableSharedFlow<List<Currency>>(1)
    val currentCurrencyListFlow = _currentCurrencyListFlow.asSharedFlow()

    private val _currencyDateFlow = MutableSharedFlow<String>(1)
    val currencyDateFlow = _currencyDateFlow.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val convertedRoot = currenciesSource.getCurrencies()
            _currentCurrencyListFlow.emit(convertedRoot.currencies)
            _currencyDateFlow.emit(convertedRoot.date)
        }
    }

}

