package com.alexeyyuditsky.exchange_rates.screens.menu

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchange_rates.model.settings.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _startActivity = MutableSharedFlow<Intent>()
    val startActivity = _startActivity.asSharedFlow()

    private val _showErrorToast = MutableSharedFlow<Unit>()
    val showErrorToast = _showErrorToast.asSharedFlow()

    init {
        viewModelScope.launch {
            _nightMode.emit(appSettings.checkNightMode())
        }
    }

    fun sendIntent(intent: Intent) = viewModelScope.launch {
        _startActivity.emit(intent)
    }

    fun showErrorToast() = viewModelScope.launch {
        _showErrorToast.emit(Unit)
    }

    fun setNightMode(isNightModeOn: Boolean) = viewModelScope.launch {
        appSettings.setNightMode(isNightModeOn)
        _nightMode.emit(isNightModeOn)
    }

}