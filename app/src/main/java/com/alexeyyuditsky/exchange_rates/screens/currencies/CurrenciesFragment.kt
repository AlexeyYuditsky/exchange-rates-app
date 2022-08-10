package com.alexeyyuditsky.exchange_rates.screens.currencies

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.adapters.CurrenciesAdapter
import com.alexeyyuditsky.exchange_rates.databinding.FragmentCurrenciesBinding
import com.alexeyyuditsky.exchange_rates.utils.currencyCodesAndNamesMap
import com.alexeyyuditsky.exchange_rates.utils.log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CurrenciesFragment : Fragment(R.layout.fragment_currencies) {

    private val viewModel by viewModels<CurrenciesViewModel>()
    private lateinit var binding: FragmentCurrenciesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrenciesBinding.bind(view)

        setupCurrenciesList()
        setupSearchInput()
    }

    private fun setupCurrenciesList() {
        val adapter = CurrenciesAdapter(viewModel)

        binding.recyclerView.adapter = adapter
        (binding.recyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                RecyclerView.VERTICAL
            )
        )

        observeCurrencies(adapter)
    }

    private fun observeCurrencies(adapter: CurrenciesAdapter) = lifecycleScope.launch {
        viewModel.currenciesFlow.collectLatest { pagingData ->
            adapter.submitData(pagingData)
        }
    }

    private fun setupSearchInput() {
        binding.searchTextInput.searchEditText.addTextChangedListener {
            val string = it.toString()
            val currencyCodesList = mutableListOf<String>()

            currencyCodesAndNamesMap.forEach { (key, value) ->
                if (value.contains(string, true) || key.contains(string, true))
                    currencyCodesList.add(key)
            }
            viewModel.setSearchBy(currencyCodesList)
        }
    }

}