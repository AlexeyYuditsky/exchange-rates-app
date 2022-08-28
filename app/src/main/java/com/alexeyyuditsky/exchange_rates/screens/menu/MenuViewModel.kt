package com.alexeyyuditsky.exchange_rates.screens.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchange_rates.model.currencies.ConverterCurrency
import com.alexeyyuditsky.exchange_rates.model.settings.AppSettings
import com.alexeyyuditsky.exchange_rates.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class MenuViewModel @Inject constructor(
    private val appSettings: AppSettings
) : ViewModel() {

    private val _nightMode = MutableSharedFlow<Boolean>()
    val nightMode = _nightMode.asSharedFlow()

    init {
        viewModelScope.launch {
            _nightMode.emit(appSettings.checkNightMode())
        }
    }

    fun setNightMode(isNightModeOn: Boolean) = viewModelScope.launch {
        appSettings.setNightMode(isNightModeOn)
        _nightMode.emit(isNightModeOn)
    }

}