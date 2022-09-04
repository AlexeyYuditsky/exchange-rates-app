package com.alexeyyuditsky.exchangerates.model.settings

interface AppSettings {

    fun checkNightMode(): Int

    fun setNightMode(mode: Int)

}