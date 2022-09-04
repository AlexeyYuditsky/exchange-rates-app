package com.alexeyyuditsky.exchangerates.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.exchangerates.model.currencies.Currency
import com.alexeyyuditsky.exchangerates.model.currencies.repositories.CurrenciesRepository
import com.alexeyyuditsky.exchangerates.screens.currencies.CurrenciesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel(), FavoriteListener {

    private val _favoriteCurrencies = MutableSharedFlow<List<Currency>>(replay = 1, extraBufferCapacity = 1)
    val favoriteCurrencies = _favoriteCurrencies.asSharedFlow()

    init {
        viewModelScope.launch {
            currenciesRepository.getFavoriteCurrencies().collectLatest {
                _favoriteCurrencies.emit(it)
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