package com.alexeyyuditsky.exchange_rates.screens.converter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.adapters.ConverterAdapter
import com.alexeyyuditsky.exchange_rates.databinding.FragmentConverterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ConverterFragment : Fragment(R.layout.fragment_converter) {

    private val viewModel by viewModels<ConverterViewModel>()
    private lateinit var binding: FragmentConverterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConverterBinding.bind(view)

        setupConverterList()
    }

    private fun setupConverterList() {
        val adapter = ConverterAdapter()

        binding.recyclerView.adapter = adapter
        (binding.recyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                RecyclerView.VERTICAL
            )
        )

        observeConverter(adapter)
    }

    private fun observeConverter(adapter: ConverterAdapter) = lifecycleScope.launch {
        viewModel.converterCurrencies.observe(viewLifecycleOwner) {
            adapter.currencies = it
        }
    }

}