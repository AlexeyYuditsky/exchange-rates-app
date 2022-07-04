package com.alexeyyuditsky.exchange_rates.model

import com.squareup.moshi.Json

class ResponseRoot(
    @field:Json(name = "date") val date: String,
    @field:Json(name = "usd") val currencies: ResponseCurrencies
)

class ResponseCurrencies(
    @field:Json(name = "rub") val rub: String,
    @field:Json(name = "eur") val eur: String,
    @field:Json(name = "gbp") val gbp: String,
    @field:Json(name = "1inch") val `1inch`: String,
    @field:Json(name = "aave") val aave: String,
    @field:Json(name = "ada") val ada: String,
    @field:Json(name = "aed") val aed: String,
    @field:Json(name = "afn") val afn: String,
    @field:Json(name = "algo") val algo: String,
    @field:Json(name = "all") val all: String,
    @field:Json(name = "amd") val amd: String,
    @field:Json(name = "amp") val amp: String,
    @field:Json(name = "ang") val ang: String,
    @field:Json(name = "aoa") val aoa: String,
    @field:Json(name = "ar") val ar: String,
    @field:Json(name = "ars") val ars: String,
    @field:Json(name = "atom") val atom: String,
    @field:Json(name = "aud") val aud: String,
)

class ConvertedRoot(
    val date: String,
    val currencies: List<Currency>
)

data class Currency(
    val name: String,
    val value: Float
)