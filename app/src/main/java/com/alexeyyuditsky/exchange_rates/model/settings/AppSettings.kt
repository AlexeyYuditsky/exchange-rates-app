package com.alexeyyuditsky.exchange_rates.model.settings

interface AppSettings {

    fun checkNightMode(): Boolean

    fun setNightMode(isNightModeOn: Boolean)

}