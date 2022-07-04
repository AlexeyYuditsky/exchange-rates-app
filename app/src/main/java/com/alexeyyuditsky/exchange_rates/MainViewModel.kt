package com.alexeyyuditsky.exchange_rates

import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.exchange_rates.model.Currency
import kotlinx.coroutines.flow.*

class MainViewModel : ViewModel() {

    private val _sharedFlow = MutableSharedFlow<List<Currency>>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun initSharedFlow(currenciesList: List<Currency>) = _sharedFlow.tryEmit(currenciesList)

}