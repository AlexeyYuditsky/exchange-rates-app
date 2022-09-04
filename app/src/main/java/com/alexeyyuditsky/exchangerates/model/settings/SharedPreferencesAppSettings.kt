package com.alexeyyuditsky.exchangerates.model.settings

import android.content.Context
import com.alexeyyuditsky.exchangerates.utils.MODE
import com.alexeyyuditsky.exchangerates.utils.SYSTEM_MODE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesAppSettings @Inject constructor(
    @ApplicationContext appContext: Context
) : AppSettings {

    private val sharedPreferences = appContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    private val sharedPrefEdit = sharedPreferences.edit()

    override fun checkNightMode(): Int {
        return sharedPreferences.getInt(MODE, SYSTEM_MODE)
    }

    override fun setNightMode(mode: Int) {
        sharedPrefEdit.putInt(MODE, mode).apply()
    }

    companion object {
        const val SETTINGS = "settings"
    }

}