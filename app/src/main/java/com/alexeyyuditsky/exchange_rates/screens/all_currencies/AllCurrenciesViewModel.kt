package com.alexeyyuditsky.exchange_rates.screens.all_currencies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexeyyuditsky.exchange_rates.model.currencies.UICurrency
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.CurrenciesRepository
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room.CurrenciesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class AllCurrenciesViewModel @Inject constructor(
    currenciesRepository: CurrenciesRepository
) : ViewModel() {

    val currenciesFlow: Flow<PagingData<UICurrency>>

    private val searchBy = MutableLiveData("")

    init {
        currenciesFlow = searchBy.asFlow()
            .flatMapLatest { currenciesRepository.getPagedCurrencies(it) }
            .cachedIn(viewModelScope)
    }

}