package com.alexeyyuditsky.exchange_rates.screens

import com.alexeyyuditsky.exchange_rates.model.currencies.Currency

interface FavoriteListener {

    fun onToggleFavoriteFlag(currency: Currency)

}