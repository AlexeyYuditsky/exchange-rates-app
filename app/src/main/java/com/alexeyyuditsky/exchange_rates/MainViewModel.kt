package com.alexeyyuditsky.exchange_rates

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchange_rates.network.CurrenciesSource
import com.alexeyyuditsky.exchange_rates.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    currenciesSource: CurrenciesSource,
    @ApplicationContext context: Context
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            loadLanguage(context)
            loadImages(context)
        }

        viewModelScope.launch {
            isUpdated = currenciesSource.getCurrenciesFromNetwork()
            _isLoading.value = false
        }
    }

}