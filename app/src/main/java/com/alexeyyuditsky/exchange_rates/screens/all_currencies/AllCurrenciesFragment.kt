package com.alexeyyuditsky.exchange_rates.screens.all_currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.adapters.CurrenciesAdapter
import com.alexeyyuditsky.exchange_rates.databinding.FragmentAllCurrenciesBinding
import com.alexeyyuditsky.exchange_rates.utils.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AllCurrenciesFragment : Fragment(R.layout.fragment_all_currencies) {

    private val viewModel by viewModels<AllCurrenciesViewModel>()
    private lateinit var binding: FragmentAllCurrenciesBinding
    private val adapter = CurrenciesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllCurrenciesBinding.bind(view)

        setupAdapter()
        observeAdapter()
        observeCurrenciesDate()
    }

    private fun observeCurrenciesDate() = lifecycleScope.launch {
        viewModel.currencyDateFlow.collectLatest {
            binding.currenciesDateTextView.text = it
        }
    }

    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                RecyclerView.VERTICAL
            )
        )
    }

    private fun observeAdapter() = lifecycleScope.launch {
        viewModel.currentCurrencyListFlow.collectLatest {
            adapter.currencies = it
            binding.shimmerFrameLayout.stopShimmer()
            binding.shimmerFrameLayout.isVisible = false
            binding.recyclerView.isVisible = true
        }
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmer()
        super.onPause()
    }

}