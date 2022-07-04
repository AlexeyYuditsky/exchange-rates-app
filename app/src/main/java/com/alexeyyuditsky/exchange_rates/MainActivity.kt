package com.alexeyyuditsky.exchange_rates

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.exchange_rates.adapters.CurrenciesAdapter
import com.alexeyyuditsky.exchange_rates.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter = CurrenciesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        Singletons.init(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter

        getCurrencies()
        observeAdapter()
    }

    private fun getCurrencies() = lifecycleScope.launch {
        viewModel.initSharedFlow(Singletons.retrofitApi.getCurrencies().currencies)
    }

    private fun observeAdapter() = lifecycleScope.launch {
        viewModel.sharedFlow.collect { adapter.currencies = it }
    }

}