package com.alexeyyuditsky.exchange_rates.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, 0)
    return sdf.format(cal.time)
}

fun getYesterdayDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, -1)
    return sdf.format(cal.time)
}