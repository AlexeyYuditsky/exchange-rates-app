package com.alexeyyuditsky.exchangerates.screens.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchangerates.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

open class BaseViewModel : ViewModel() {

    private val _showErrorMessage = MutableSharedFlow<Int>()
    val showErrorMessage = _showErrorMessage.asSharedFlow()

    fun safeLaunch(block: suspend CoroutineScope.(date: Int) -> Unit) = viewModelScope.launch {
        try {
            block.invoke(this, 0)
        } catch (e: UnknownHostException) {
            _showErrorMessage.emit(R.string.no_internet)
        } catch (e: ConnectException) {
            _showErrorMessage.emit(R.string.connection_error)
        } catch (e: SSLHandshakeException) {
            _showErrorMessage.emit(R.string.check_date)
        } catch (e: SocketTimeoutException) {
            _showErrorMessage.emit(R.string.check_date)
        } catch (e: HttpException) {
            block.invoke(this, -1)
        } catch (e: Exception) {
            _showErrorMessage.emit(R.string.internal_error)
        }
    }

}