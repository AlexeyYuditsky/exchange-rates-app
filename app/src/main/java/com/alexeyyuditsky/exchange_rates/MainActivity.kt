package com.alexeyyuditsky.exchange_rates

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.exchange_rates.adapters.CurrenciesAdapter
import com.alexeyyuditsky.exchange_rates.databinding.ActivityMainBinding
import com.alexeyyuditsky.exchange_rates.utils.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter = CurrenciesAdapter()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        Singletons.init(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.yesterdayCurrencyList.addAll(Singletons.retrofitApi.getCurrencies(yesterdayDate).currencies)
            viewModel.currentCurrencyList.addAll(Singletons.retrofitApi.getCurrencies(currentDate).currencies)

            viewModel.yesterdayCurrencyList.forEach { if (it.name == "rub") yesterdayRubAgainstDollar = it.value }
            viewModel.currentCurrencyList.forEach { if (it.name == "rub") currentRubAgainstDollar = it.value }

            viewModel.currentCurrencyList.removeIf { it.name == "rub" }
            viewModel.yesterdayCurrencyList.removeIf { it.name == "rub" }

            viewModel.currentCurrencyList.forEach { if (it.name == "usd") it.name = "rub" }
            viewModel.yesterdayCurrencyList.forEach { if (it.name == "usd") it.name = "rub" }

            viewModel.initCurrencies()
        }

        observeAdapter()
    }

    private fun observeAdapter() = lifecycleScope.launch {
        viewModel.currentCurrencyListFlow.collect { adapter.currencies = it }
    }

}