package com.alexeyyuditsky.exchange_rates.utils

import java.text.SimpleDateFormat
import java.util.*

fun getFreshDate(amount: Int = 0): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, amount)
    return sdf.format(cal.time)
}