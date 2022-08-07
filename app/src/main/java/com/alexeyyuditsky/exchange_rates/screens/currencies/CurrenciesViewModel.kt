package com.alexeyyuditsky.exchange_rates.screens.currencies

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexeyyuditsky.exchange_rates.MainViewModel
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.CurrenciesRepository
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room.CurrenciesDao
import com.alexeyyuditsky.exchange_rates.network.CurrenciesSource
import com.alexeyyuditsky.exchange_rates.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    currenciesRepository: CurrenciesRepository,
    @ApplicationContext context: Context
) : ViewModel() {

    val list1 = mutableListOf<String>()

    val currenciesFlow: Flow<PagingData<Currency>>
    private val searchBy = MutableLiveData(emptyList<String>())

    init {
        context.resources.getStringArray(R.array.currency_names_array).forEach {
            list1.add(it.split("|")[0])
        }
        searchBy.value = list1

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

    fun setSearchBy(value: List<String>) {
        //if (this.searchBy.value == value) return
        this.searchBy.value = value
    }

}