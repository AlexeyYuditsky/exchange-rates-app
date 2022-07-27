package com.alexeyyuditsky.exchange_rates.model.currencies

data class Currency(
    val id: Long,
    val shortName: String,
    val fullName: String,
    val valueToday: String,
    val valueTodayMinusYesterday: String
)