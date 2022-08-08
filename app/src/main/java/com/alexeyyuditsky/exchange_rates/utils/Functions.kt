package com.alexeyyuditsky.exchange_rates.utils

import android.content.Context
import android.util.Log
import com.alexeyyuditsky.exchange_rates.R
import java.text.SimpleDateFormat
import java.util.*

val currencyCodesList = mutableListOf<String>()
val currencyCodesAndNamesMap = hashMapOf<String, String>()

fun loadLanguages(context: Context) {
    val stringsArray = context.resources.getStringArray(R.array.currency_names_array)
    stringsArray.forEach {
        currencyCodesList.add(it.split("|")[0])
    }
    stringsArray.forEach {
        val value = it.split("|")
        currencyCodesAndNamesMap[value[0]] = value[1]
    }
}

fun getLatestDate(amount: Int = 0): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, amount)
    return sdf.format(cal.time)
}

fun <T> log(message: T) {
    Log.d("MyLog", message.toString())
}