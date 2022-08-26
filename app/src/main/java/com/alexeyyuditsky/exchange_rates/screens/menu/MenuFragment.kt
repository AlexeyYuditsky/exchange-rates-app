package com.alexeyyuditsky.exchange_rates.screens.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.FragmentMenuBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private lateinit var binding: FragmentMenuBinding

    private val appSettingsPrefs by lazy { requireActivity().getSharedPreferences(APP_SETTING, 0) }
    private val sharedPrefsEdit by lazy { appSettingsPrefs.edit() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val isNightModeOn = checkNightMode()
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(view)
        binding.nightModeSwitch.isChecked = isNightModeOn

        binding.nightMode.setOnClickListener {
            if (isNightModeOn) {
                sharedPrefsEdit.putBoolean(NIGHT_MODE, false).apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                sharedPrefsEdit.putBoolean(NIGHT_MODE, true).apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun checkNightMode(): Boolean {
        val isNightModeOn = appSettingsPrefs.getBoolean(NIGHT_MODE, false)
        if (isNightModeOn) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        return isNightModeOn
    }

    private companion object {
        const val APP_SETTING = "app_setting_prefs"
        const val NIGHT_MODE = "night_mode"
    }

}