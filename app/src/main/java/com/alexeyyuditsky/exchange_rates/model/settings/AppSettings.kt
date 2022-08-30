package com.alexeyyuditsky.exchange_rates.model.settings

interface AppSettings {

    fun checkNightMode(): Int

    fun setNightMode(mode: Int)

}