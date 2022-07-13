package com.alexeyyuditsky.exchange_rates.utils

import java.text.SimpleDateFormat
import java.util.*

val currentDate = currentDate()
val yesterdayDate = yesterdayDate()

fun currentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, 0)
    return sdf.format(cal.time)
}

fun yesterdayDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, -1)
    return sdf.format(cal.time)
}