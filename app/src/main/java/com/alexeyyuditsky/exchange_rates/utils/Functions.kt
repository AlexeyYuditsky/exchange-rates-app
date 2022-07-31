package com.alexeyyuditsky.exchange_rates.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun getLatestDate(amount: Int = 0): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    val cal = Calendar.getInstance()

    // exchange rates are not updated on weekends
    return if (Calendar.DAY_OF_WEEK == 7) {
        if (amount == 0) {
            cal.add(Calendar.DATE, -1)
            sdf.format(cal.time)
        } else {
            cal.add(Calendar.DATE, -2)
            sdf.format(cal.time)
        }
    } else {
        cal.add(Calendar.DATE, amount)
        sdf.format(cal.time)
    }
}

fun <T> log(message: T) {
    Log.d("MyLog", message.toString())
}