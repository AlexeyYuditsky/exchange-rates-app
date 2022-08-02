package com.alexeyyuditsky.exchange_rates.screens.currencies

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexeyyuditsky.exchange_rates.MainViewModel
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.CurrenciesRepository
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room.CurrenciesDao
import com.alexeyyuditsky.exchange_rates.network.CurrenciesSource
import com.alexeyyuditsky.exchange_rates.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    currenciesRepository: CurrenciesRepository
) : ViewModel() {

    val currenciesFlow: Flow<PagingData<Currency>>
    private val searchBy = MutableLiveData("")

    init {
        viewModelScope.launch {
            while (!MainViewModel.isUpdated) {
                delay(100)
            }
            refresh()
        }

        currenciesFlow = searchBy.asFlow()
            .debounce(100)
            .flatMapLatest {
                currenciesRepository.getPagedCurrencies(it)
            }
            .cachedIn(viewModelScope)
    }

    private fun refresh() {
        this.searchBy.value = this.searchBy.value
    }

    fun setSearchBy(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.value = value
    }

}