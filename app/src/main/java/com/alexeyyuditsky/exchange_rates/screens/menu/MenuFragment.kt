package com.alexeyyuditsky.exchange_rates.screens.menu

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.exchange_rates.BuildConfig
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.FragmentMenuBinding
import com.alexeyyuditsky.exchange_rates.utils.log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu) {

    private lateinit var binding: FragmentMenuBinding

    private val viewModel by viewModels<MenuViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(view)
        binding.appVersionTextView.text = getString(R.string.version, BuildConfig.VERSION_NAME)
        binding.nightMode.setOnClickListener { onNightModeSwitchPressed() }

        observeNightMode()
    }

    private fun observeNightMode() = lifecycleScope.launch {
        viewModel.nightMode.collect {
            binding.nightModeSwitch.isChecked = it
            if (it) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun onNightModeSwitchPressed() = lifecycleScope.launch {
        val switchState = !binding.nightModeSwitch.isChecked
        binding.nightModeSwitch.isChecked = switchState
        delay(250) // waiting for the switching animation
        viewModel.setNightMode(switchState)
    }

}