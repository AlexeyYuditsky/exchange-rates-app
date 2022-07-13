package com.alexeyyuditsky.exchange_rates.utils

// Формула для нахождения разницы валют относительно сегодня и вчера
fun getCurrencyChangeRelativeToTodayAndYesterday(currentCurrencyValue: Float, yesterdayCurrencyValue: Float): String {
    return ((currentRubleExchangeRate / currentCurrencyValue) - (yesterdayRubleExchangeRate / yesterdayCurrencyValue)).toString()
}

fun main() {
    val a = 23357.496f
    val b = 23355.01f

    val yesterday = 63.07505f
    val current = 64.99989f

    val res1 = current / a
    val res2 = yesterday / b

    val t = (res1 - res2).toBigDecimal()
    println(t)

    val res = String.format("%.4f", res1 - res2).replace(',', '.').toBigDecimal()

    println(res)

}