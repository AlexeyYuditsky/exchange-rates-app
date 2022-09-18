package com.alexeyyuditsky.exchangerates.screens.currencies

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.alexeyyuditsky.exchangerates.model.currencies.Currency
import com.alexeyyuditsky.exchangerates.model.currencies.repositories.CurrenciesRepository
import com.alexeyyuditsky.exchangerates.screens.favorite.FavoriteListener
import com.alexeyyuditsky.exchangerates.utils.codesList
import com.alexeyyuditsky.exchangerates.utils.isUpdatedCurrencies
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
) : ViewModel(), FavoriteListener {

    private val localChangesFlow = MutableStateFlow(OnChange(localChanges))

    val currenciesFlow: Flow<PagingData<Currency>>
    private val searchBy = MutableLiveData(codesList)

    init {
        // every 100ms we try to display a list of currencies when data over the network is received and processed
        viewModelScope.launch {
            while (!isUpdatedCurrencies) {
                delay(100)
                refresh()
            }
        }

        val originCurrenciesFlow = searchBy.asFlow()
            .debounce(300)
            .flatMapLatest { currenciesRepository.getPagedCurrencies(it) }
            .cachedIn(viewModelScope)

        currenciesFlow = combine(
            originCurrenciesFlow,
            localChangesFlow,
            this::merge
        )
    }

    private fun merge(currencies: PagingData<Currency>, onChange: OnChange): PagingData<Currency> {
        return currencies.map { currency ->
            val localFavoriteFlag = onChange.localChanges.favoriteFlags[currency.code]

            return@map if (localFavoriteFlag == null) {
                currency
            } else {
                currency.copy(isFavorite = localFavoriteFlag)
            }
        }
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

    companion object {
        val localChanges = LocalChanges()
    }

}