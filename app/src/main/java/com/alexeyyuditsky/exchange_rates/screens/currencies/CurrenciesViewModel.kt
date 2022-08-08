package com.alexeyyuditsky.exchange_rates.screens.currencies

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexeyyuditsky.exchange_rates.MainViewModel
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.CurrenciesRepository
import com.alexeyyuditsky.exchange_rates.utils.currencyCodesList
import com.alexeyyuditsky.exchange_rates.utils.loadLanguages
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    currenciesRepository: CurrenciesRepository,
    @ApplicationContext context: Context
) : ViewModel() {

    val currenciesFlow: Flow<PagingData<Currency>>
    private val searchBy = MutableLiveData(currencyCodesList)

    init {
        viewModelScope.launch { loadLanguages(context) }

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

    fun setSearchBy(value: MutableList<String>) {
        //if (this.searchBy.value == value) return
        this.searchBy.value = value
    }

}