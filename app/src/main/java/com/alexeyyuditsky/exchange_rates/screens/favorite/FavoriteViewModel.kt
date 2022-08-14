package com.alexeyyuditsky.exchange_rates.screens.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.CurrenciesRepository
import com.alexeyyuditsky.exchange_rates.screens.FavoriteListener
import com.alexeyyuditsky.exchange_rates.screens.currencies.CurrenciesViewModel
import com.alexeyyuditsky.exchange_rates.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel(), FavoriteListener {

    private val _favoriteCurrencies = MutableLiveData<List<Currency>>()
    val favoriteCurrencies = _favoriteCurrencies as LiveData<List<Currency>>

    init {
        viewModelScope.launch {
            currenciesRepository.getFavoriteCurrencies().collectLatest {
                _favoriteCurrencies.value = it
            }
        }
    }

    override fun onToggleFavoriteFlag(currency: Currency) {
        viewModelScope.launch {
            val newFlagValue = !currency.isFavorite
            currenciesRepository.setIsFavoriteCurrency(currency, newFlagValue)
            CurrenciesViewModel.localChanges.favoriteFlags[currency.code] = newFlagValue
        }
    }

}