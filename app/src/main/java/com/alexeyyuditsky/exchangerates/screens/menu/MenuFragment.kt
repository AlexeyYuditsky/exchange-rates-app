package com.alexeyyuditsky.exchangerates.screens.menu

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.exchangerates.BuildConfig
import com.alexeyyuditsky.exchangerates.R
import com.alexeyyuditsky.exchangerates.databinding.FragmentMenuBinding
import com.alexeyyuditsky.exchangerates.utils.*
import com.bumptech.glide.Glide
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

    private val viewModel by viewModels<MenuViewModel>()
    private lateinit var binding: FragmentMenuBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(view).apply {
            appVersionTextView.text = getString(R.string.version, BuildConfig.VERSION_NAME)
            nightModeSwitch.isChecked = checkNightMode()
            nightModeLayout.setOnClickListener { onNightModeSwitchPressed() }
            shareLayout.setOnClickListener { onShareButtonPressed() }
            rateAppLayout.setOnClickListener { onRateAppButtonPressed() }
            writeVKLayout.setOnClickListener { onWriteVKButtonPressed() }
        }

        setAppImage()

        observeNightMode()
        observeStartNewActivity()
        observeShowErrorToast()
    }

    private fun setAppImage() {
        Glide.with(this)
            .load(R.drawable.icon)
            .circleCrop()
            .into(binding.appImageView)
    }

    private fun checkNightMode(): Boolean {
        return (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    private fun observeNightMode() = lifecycleScope.launch {
        viewModel.nightMode.collectLatest { requireActivity().recreate() }
    }

    private fun observeStartNewActivity() = lifecycleScope.launch {
        viewModel.startNewActivity.collectLatest { startActivity(it) }
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
        viewModel.setNightMode(if (switchState) NIGHT_MODE else LITE_MODE)
    }

    private fun onShareButtonPressed() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
        }
        viewModel.launchActivity(::findRequiredActivity, intent)
    }

    private fun onRateAppButtonPressed() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_ADDRESS))
        viewModel.launchActivity(::findRequiredActivity, intent)
    }

    private fun onWriteVKButtonPressed() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(VK_ADDRESS))
        viewModel.launchActivity(::findRequiredActivity, intent)
    }

    private fun findRequiredActivity(intent: Intent): List<ResolveInfo> {
        return requireContext().packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
    }

    private companion object {
        const val VK_ADDRESS = "https://vk.com/alexeyyuditsky"
        const val GOOGLE_PLAY_ADDRESS = "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
    }

}

