package com.alexeyyuditsky.exchange_rates.utils

// Формула для нахождения курса любой валюты относительно рубля
fun getRubleValueFromCurrency(currencyValue: Float): Float {
    return currentRubleExchangeRate / currencyValue
}

// Формула для нахождения разницы валют относительно сегодня и вчера
fun getCurrencyChangeRelativeToTodayAndYesterday(currentCurrencyValue: Float, yesterdayCurrencyValue: Float): Float {
    return (currentRubleExchangeRate / currentCurrencyValue) - (yesterdayRubleExchangeRate / yesterdayCurrencyValue)
}