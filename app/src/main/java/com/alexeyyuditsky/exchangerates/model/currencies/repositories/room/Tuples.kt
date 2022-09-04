package com.alexeyyuditsky.exchangerates.model.currencies.repositories.room

data class UpdateCurrencyFavoriteFlagTuple(
    val code: String,
    val isFavorite: Boolean
)

data class UpdateCurrencyValueTuple(
    val code: String,
    val valueToday: String,
    val valueDifference: String
)