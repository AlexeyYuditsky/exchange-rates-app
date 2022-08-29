package com.alexeyyuditsky.exchange_rates.screens.menu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.exchange_rates.BuildConfig
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.FragmentMenuBinding
import com.alexeyyuditsky.exchange_rates.utils.GOOGLE_PLAY_ADDRESS
import com.alexeyyuditsky.exchange_rates.utils.VK_ADDRESS
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
        binding = FragmentMenuBinding.bind(view).apply {
            appVersionTextView.text = getString(R.string.version, BuildConfig.VERSION_NAME)
            nightModeLayout.setOnClickListener { onNightModeSwitchPressed() }
            shareLayout.setOnClickListener { onShareButtonPressed() }
            rateAppLayout.setOnClickListener { onRateAppButtonPressed() }
            writeVKLayout.setOnClickListener { onWriteVKButtonPressed() }
        }

        observeNightMode()
        observeStartActivity()
        observeShowErrorToast()
    }

    private fun observeNightMode() = lifecycleScope.launch {
        viewModel.nightMode.collectLatest {
            binding.nightModeSwitch.isChecked = it
            if (it) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun observeStartActivity() = lifecycleScope.launch {
        viewModel.startActivity.collectLatest {
            startActivity(it)
        }
    }

    private fun observeShowErrorToast() = lifecycleScope.launch {
        viewModel.showErrorToast.collectLatest {
            Toast.makeText(requireContext(), getString(R.string.no_app_for_open), Toast.LENGTH_SHORT).show()
        }
    }

    private fun onNightModeSwitchPressed() = lifecycleScope.launch {
        val switchState = !binding.nightModeSwitch.isChecked
        binding.nightModeSwitch.isChecked = switchState
        delay(250) // waiting for the switching animation
        viewModel.setNightMode(switchState)
    }

    private fun onShareButtonPressed() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
        }
        findRequiredActivity(intent)?.let { viewModel.sendIntent(intent) } ?: viewModel.showErrorToast()
    }

    private fun onRateAppButtonPressed() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_ADDRESS))
        findRequiredActivity(intent)?.let { viewModel.sendIntent(intent) } ?: viewModel.showErrorToast()
    }

    private fun onWriteVKButtonPressed() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(VK_ADDRESS))
        findRequiredActivity(intent)?.let { viewModel.sendIntent(intent) } ?: viewModel.showErrorToast()
    }

    private fun findRequiredActivity(intent: Intent) = intent.resolveActivity(requireContext().packageManager)

}