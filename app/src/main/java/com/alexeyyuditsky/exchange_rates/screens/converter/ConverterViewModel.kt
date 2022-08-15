package com.alexeyyuditsky.exchange_rates.screens.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.CurrenciesRepository
import com.alexeyyuditsky.exchange_rates.screens.FavoriteListener
import com.alexeyyuditsky.exchange_rates.screens.currencies.CurrenciesViewModel
import com.alexeyyuditsky.exchange_rates.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel() {

    private val _converterCurrencies = MutableLiveData<List<Currency>>()
    val converterCurrencies = _converterCurrencies as LiveData<List<Currency>>

    init {
        viewModelScope.launch {
            currenciesRepository.getFavoriteCurrencies().collectLatest {
                _converterCurrencies.value = formatCurrencyList(it.toMutableSet())
            }
        }
    }

    private suspend fun formatCurrencyList(favoriteList: MutableSet<Currency>): List<Currency> {
        val currencyList = currenciesRepository.getCurrencies()
        favoriteList.addAll(currencyList)
        val finalList = favoriteList.toMutableList()
        finalList.add(0, Currency(code = "RUB", "0", "0", false))

        return finalList.toList()
    }

}