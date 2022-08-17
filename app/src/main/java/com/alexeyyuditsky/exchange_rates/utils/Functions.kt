package com.alexeyyuditsky.exchange_rates.utils

import android.content.Context
import android.util.Log
import com.alexeyyuditsky.exchange_rates.R
import java.text.SimpleDateFormat
import java.util.*

var isUpdated = false

val currencyCodesList = mutableListOf<String>()
val currencyCodesAndNamesMap = hashMapOf<String, String>()

val currencyImagesMap = hashMapOf<String, Int>()

fun loadLanguage(context: Context) {
    val stringsArray = context.resources.getStringArray(R.array.currency_names_array)
    stringsArray.forEach {
        currencyCodesList.add(it.split("|")[0])
    }
    stringsArray.forEach {
        val value = it.split("|")
        currencyCodesAndNamesMap[value[0]] = value[1]
    }
    currencyCodesAndNamesMap["RUB"] = context.getString(R.string.russian_ruble)
}

fun loadImages(context: Context) {
    currencyCodesAndNamesMap.forEach { (key, _) ->
        val id = context.resources.getIdentifier(key.lowercase(), "drawable", context.packageName)
        currencyImagesMap[key] = if (id == 0) R.drawable._try else id
    }
    currencyImagesMap["RUB"] = R.drawable.rub
}

fun getLatestDate(amount: Int = 0): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, amount)
    return sdf.format(cal.time)
}

fun log(message: String) {
    Log.d("MyLog", message)
}