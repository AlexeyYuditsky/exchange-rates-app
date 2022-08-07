package com.alexeyyuditsky.exchange_rates.model.currencies

data class Currency(
    val id: Long,
    val code: String,
    val valueToday: String,
    val valueDifference: String
)