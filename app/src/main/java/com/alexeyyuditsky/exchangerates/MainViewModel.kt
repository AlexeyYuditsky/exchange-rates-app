package com.alexeyyuditsky.exchangerates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchangerates.model.network.CurrenciesSource
import com.alexeyyuditsky.exchangerates.utils.isUpdatedRates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    currenciesSource: CurrenciesSource
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                isUpdatedRates = currenciesSource.getCurrenciesFromNetwork()
            }
            launch {
                delay(100)
                _isLoading.value = false
            }
        }
    }

}