package com.alexeyyuditsky.exchange_rates.screens.all_currencies

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.Singletons
import com.alexeyyuditsky.exchange_rates.adapters.CurrenciesAdapter
import com.alexeyyuditsky.exchange_rates.databinding.FragmentAllCurrenciesBinding
import com.alexeyyuditsky.exchange_rates.model.Currency
import com.alexeyyuditsky.exchange_rates.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllCurrenciesFragment : Fragment(R.layout.fragment_all_currencies) {

    private val viewModel by viewModels<AllCurrenciesViewModel>()
    private lateinit var binding: FragmentAllCurrenciesBinding
    private val adapter = CurrenciesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllCurrenciesBinding.bind(view)

        setupAdapter()
        initData()
        observeAdapter()
    }

    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(binding.recyclerView.context, RecyclerView.VERTICAL)
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initData() = lifecycleScope.launch {
        val yesterdayConvertedCurrency = withContext(Dispatchers.IO) {
            Singletons.retrofitApi.getCurrencies(yesterdayDate)

        }
        val currentConvertedCurrency = withContext(Dispatchers.IO) {
            Singletons.retrofitApi.getCurrencies(currentDate)
        }

        viewModel.yesterdayCurrencyList.clear()
        viewModel.currentCurrencyList.clear()

        viewModel.yesterdayCurrencyList.addAll(yesterdayConvertedCurrency.currencies)
        viewModel.currentCurrencyList.addAll(currentConvertedCurrency.currencies)

        viewModel.yesterdayCurrencyList.forEach { if (it.name == "rub") yesterdayRubleExchangeRate = it.value }
        viewModel.currentCurrencyList.forEach { if (it.name == "rub") currentRubleExchangeRate = it.value }

        viewModel.currentCurrencyList.removeIf { it.name == "rub" }
        viewModel.yesterdayCurrencyList.removeIf { it.name == "rub" }

        viewModel.currentCurrencyList.forEach { if (it.name == "usd") it.name = "rub" }
        viewModel.yesterdayCurrencyList.forEach { if (it.name == "usd") it.name = "rub" }

        viewModel.initCurrencies()

        binding.currenciesDateTextView.text = currentConvertedCurrency.date
    }

    private fun observeAdapter() = lifecycleScope.launch {
        viewModel.currentCurrencyListFlow.collect {
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