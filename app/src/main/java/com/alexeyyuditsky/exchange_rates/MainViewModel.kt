package com.alexeyyuditsky.exchange_rates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchange_rates.network.CurrenciesSource
import com.alexeyyuditsky.exchange_rates.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    currenciesSource: CurrenciesSource
) : ViewModel() {

    init {
        viewModelScope.launch {
            log("Запрос данных с сервера")
            currenciesSource.getCurrenciesFromNetwork()
        }
    }

}