package com.alexeyyuditsky.exchangerates.utils

import android.content.Context
import android.util.Log
import com.alexeyyuditsky.exchangerates.R
import java.text.SimpleDateFormat
import java.util.*

var isUpdatedCurrencies = false

val codesList = mutableListOf<String>()
val codesAndNamesMap = hashMapOf<String, String>()
val imagesMap = hashMapOf("RUB" to R.drawable.rub)

fun loadLanguage(context: Context) {
    val stringsArray = context.resources.getStringArray(R.array.currency_names_array)
    stringsArray.forEach {
        codesList.add(it.split("|")[0])
    }
    stringsArray.forEach {
        val value = it.split("|")
        codesAndNamesMap[value[0]] = value[1]
    }
    codesAndNamesMap["RUB"] = context.getString(R.string.russian_ruble)
}

fun loadImages(context: Context) = codesAndNamesMap.forEach { (key, _) ->
    val id =
        if (key == "TRY") context.resources.getIdentifier("_${key.lowercase()}", "drawable", context.packageName)
        else context.resources.getIdentifier(key.lowercase(), "drawable", context.packageName)
    imagesMap[key] = if (id != 0) id else R.drawable.ic_error
}

fun getLatestDate(amount: Int): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, amount)
    return sdf.format(cal.time)
}

fun <T> log(message: T) {
    Log.d("MyLog", message.toString())
}