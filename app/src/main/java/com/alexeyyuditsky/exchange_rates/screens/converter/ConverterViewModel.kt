package com.alexeyyuditsky.exchange_rates.screens.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchange_rates.model.currencies.ConverterCurrency
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.CurrenciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel() {

    private val _converterCurrencies = MutableSharedFlow<List<ConverterCurrency>>(1, 1)
    val converterCurrencies = _converterCurrencies.asSharedFlow()

    init {
        viewModelScope.launch {
            currenciesRepository.getFavoriteCurrencies().collectLatest {
                _converterCurrencies.emit(formatCurrencyList(it.toMutableList()))
            }
        }
    }

    private suspend fun formatCurrencyList(favoriteList: MutableList<Currency>): List<ConverterCurrency> {
        val currencyList = currenciesRepository.getCurrencies()
        favoriteList.addAll(currencyList)
        val newCurrencyList = favoriteList.map { it.toConverterCurrency() }.toMutableList()
        // make the ruble the first currency
        newCurrencyList.add(0, ConverterCurrency(code = "RUB"))

        return newCurrencyList
    }

}