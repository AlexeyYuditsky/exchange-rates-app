package com.alexeyyuditsky.exchangerates

import com.alexeyyuditsky.exchangerates.model.network.CurrenciesSource
import com.alexeyyuditsky.exchangerates.utils.isUpdatedCurrencies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    currenciesSource: CurrenciesSource
) : BaseViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        safeLaunch { date ->
            _isLoading.value = false
            isUpdatedCurrencies = currenciesSource.getCurrenciesFromNetwork(date)
        }
    }

}