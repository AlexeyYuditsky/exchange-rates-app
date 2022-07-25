package com.alexeyyuditsky.exchange_rates.screens.all_currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchange_rates.network.CurrenciesSource
import com.alexeyyuditsky.exchange_rates.network.Currency
import com.alexeyyuditsky.exchange_rates.room.entities.UICurrency
import com.alexeyyuditsky.exchange_rates.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class AllCurrenciesViewModel @Inject constructor(
    currenciesSource: CurrenciesSource
) : ViewModel() {

    private val _currentCurrencyListFlow = MutableSharedFlow<List<UICurrency>>(1)
    val currentCurrencyListFlow = _currentCurrencyListFlow.asSharedFlow()

    private val _currencyDateFlow = MutableSharedFlow<String>(1)
    val currencyDateFlow = _currencyDateFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            currenciesSource.getCurrenciesFromNetwork()
            _currentCurrencyListFlow.emit(currenciesSource.getCurrenciesFromDatabase())
            _currencyDateFlow.emit(currenciesSource.getCurrenciesDate())
        }
    }

}

