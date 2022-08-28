package com.alexeyyuditsky.exchange_rates.model.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.alexeyyuditsky.exchange_rates.screens.menu.MenuFragment
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesAppSettings @Inject constructor(
    @ApplicationContext appContext: Context
) : AppSettings {

    private val sharedPreferences = appContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    private val sharedPrefEdit = sharedPreferences.edit()

    override fun checkNightMode(): Boolean {
        return sharedPreferences.getBoolean(NIGHT_MODE, false)
    }

    override fun setNightMode(isNightModeOn: Boolean) {
        sharedPrefEdit.putBoolean(NIGHT_MODE, isNightModeOn).apply()
    }

    private companion object {
        const val SETTINGS = "settings"
        const val NIGHT_MODE = "night_mode"
    }


}