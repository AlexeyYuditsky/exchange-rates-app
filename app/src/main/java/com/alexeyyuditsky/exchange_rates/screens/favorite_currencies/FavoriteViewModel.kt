package com.alexeyyuditsky.exchange_rates.screens.favorite_currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchange_rates.adapters.FavoritesAdapter
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.CurrenciesRepository
import com.alexeyyuditsky.exchange_rates.utils.deleteCodesMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel(), FavoritesAdapter.Listener {

    private val _favoriteCurrencies = MutableLiveData<List<Currency>>()
    val favoriteCurrencies = _favoriteCurrencies as LiveData<List<Currency>>

    init {
        viewModelScope.launch {
            currenciesRepository.getFavoriteCurrencies().collect {
                _favoriteCurrencies.value = it
            }
        }
    }

    override fun onToggleFavoriteFlag(currency: Currency) {
        viewModelScope.launch {
            deleteCodesMap.clear()
            deleteCodesMap[currency.code] = true
            currenciesRepository.setIsFavoriteCurrency(currency, !currency.isFavorite)
        }
    }

}