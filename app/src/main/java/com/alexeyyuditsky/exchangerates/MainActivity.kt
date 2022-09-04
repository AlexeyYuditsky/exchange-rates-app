package com.alexeyyuditsky.exchangerates

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.alexeyyuditsky.exchangerates.databinding.ActivityMainBinding
import com.alexeyyuditsky.exchangerates.model.settings.SharedPreferencesAppSettings.Companion.SETTINGS
import com.alexeyyuditsky.exchangerates.screens.currencies.CurrenciesFragment
import com.alexeyyuditsky.exchangerates.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val sharedPreferences by lazy { getSharedPreferences(SETTINGS, Context.MODE_PRIVATE) }

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is CurrenciesFragment) supportActionBar?.hide() else supportActionBar?.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme()
        super.onCreate(savedInstanceState)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)
        loadLanguage(this)
        loadImages(this)
        installSplashScreen().apply { setKeepOnScreenCondition { viewModel.isLoading.value } }
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        super.onDestroy()
    }

    private fun setTheme() {
        val mode = sharedPreferences.getInt(MODE, SYSTEM_MODE)
        if (mode == NIGHT_MODE) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else if (mode == LITE_MODE) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}