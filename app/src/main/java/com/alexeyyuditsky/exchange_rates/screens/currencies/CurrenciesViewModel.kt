package com.alexeyyuditsky.exchange_rates.screens.currencies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.alexeyyuditsky.exchange_rates.adapters.CurrenciesAdapter
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.CurrenciesRepository
import com.alexeyyuditsky.exchange_rates.utils.currencyCodesList
import com.alexeyyuditsky.exchange_rates.utils.deleteCodesMap
import com.alexeyyuditsky.exchange_rates.utils.isUpdated
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
    private val currenciesRepository: CurrenciesRepository
) : ViewModel(), CurrenciesAdapter.Listener {

    private val localChanges = LocalChanges()
    private val localChangesFlow = MutableStateFlow(OnChange(localChanges))

    val currenciesFlow: Flow<PagingData<Currency>>
    private val searchBy = MutableLiveData(currencyCodesList)

    init {
        // пытаемся получить список валют, но получим его, когда данные по сети будут получены и обработаны
        viewModelScope.launch {
            while (!isUpdated) {
                delay(100)
            }
            refresh()
        }

        val originCurrenciesFlow = searchBy.asFlow()
            .debounce(300)
            .flatMapLatest {
                currenciesRepository.getPagedCurrencies(it)
            }
            .cachedIn(viewModelScope)

        currenciesFlow = combine(
            originCurrenciesFlow,
            localChangesFlow,
            this::merge
        )
    }

    private fun merge(currencies: PagingData<Currency>, onChange: OnChange): PagingData<Currency> {
        val res = currencies.map { currency ->
            if (deleteCodesMap[currency.code] == onChange.localChanges.favoriteFlags[currency.code]) {
                return@map currency.copy(isFavorite = false)
            } else {
                val localFavoriteFlag = onChange.localChanges.favoriteFlags[currency.code]
                if (currency.code == "AED") {
                    log("merge $currency")
                    log("localFavoriteFlag = $localFavoriteFlag")
                }

                return@map if (localFavoriteFlag == null) {
                    currency
                } else {
                    currency.copy(isFavorite = localFavoriteFlag)
                }
            }
        }
        return res
    }

    private fun refresh() {
        this.searchBy.value = this.searchBy.value
    }

    fun setSearchBy(value: MutableList<String>) {
        if (this.searchBy.value == value) return
        this.searchBy.value = value
    }

    override fun onToggleFavoriteFlag(currency: Currency) {
        viewModelScope.launch {
            val newFlagValue = !currency.isFavorite
            currenciesRepository.setIsFavoriteCurrency(currency, newFlagValue)
            localChanges.favoriteFlags[currency.code] = newFlagValue
            localChangesFlow.value = OnChange(localChanges)
        }
    }

    class OnChange(val localChanges: LocalChanges)

    class LocalChanges {
        val favoriteFlags = mutableMapOf<String, Boolean>()
    }

}