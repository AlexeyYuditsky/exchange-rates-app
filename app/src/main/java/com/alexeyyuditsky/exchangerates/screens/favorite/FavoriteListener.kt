package com.alexeyyuditsky.exchangerates.screens.favorite

import com.alexeyyuditsky.exchangerates.model.currencies.Currency

interface FavoriteListener {

    fun onToggleFavoriteFlag(currency: Currency)

}