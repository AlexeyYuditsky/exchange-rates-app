package com.alexeyyuditsky.exchange_rates.screens.menu

import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchange_rates.model.settings.AppSettings
import com.alexeyyuditsky.exchange_rates.utils.NIGHT_MODE
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

    private val _nightMode = MutableSharedFlow<Unit>()
    val nightMode = _nightMode.asSharedFlow()

    private val _showErrorToast = MutableSharedFlow<Unit>()
    val showErrorToast = _showErrorToast.asSharedFlow()

    private val _startNewActivity = MutableSharedFlow<Intent>()
    val startNewActivity = _startNewActivity.asSharedFlow()

    fun launchActivity(findRequiredActivities: (Intent) -> List<ResolveInfo>, intent: Intent) = viewModelScope.launch {
        val listActivities = findRequiredActivities(intent)
        if (listActivities.isEmpty()) _showErrorToast.emit(Unit) else _startNewActivity.emit(intent)
    }

    fun setNightMode(mode: Int) = viewModelScope.launch {
        appSettings.setNightMode(mode)
        _nightMode.emit(Unit)
    }

}