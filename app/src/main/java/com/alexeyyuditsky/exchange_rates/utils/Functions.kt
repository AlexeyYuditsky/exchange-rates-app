package com.alexeyyuditsky.exchange_rates.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun getLatestDate(amount: Int = 0): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    val cal = Calendar.getInstance()

    val sdfDayOfWeek = SimpleDateFormat("u", Locale.ROOT)
    val dayOfWeek = sdfDayOfWeek.format(cal.time)

    // exchange rates are not updated on weekends
    return if (dayOfWeek == "7") {
        if (amount == 0) {
            cal.add(Calendar.DATE, -1)
            sdfDayOfWeek.format(cal.time)
        } else {
            cal.add(Calendar.DATE, -2)
            sdfDayOfWeek.format(cal.time)
        }
    } else {
        cal.add(Calendar.DATE, amount)
        sdf.format(cal.time)
    }
}

fun <T> log(message: T) {
    Log.d("MyLog", message.toString())
}