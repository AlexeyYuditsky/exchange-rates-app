package com.alexeyyuditsky.exchangerates.screens.currencies

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchangerates.R
import com.alexeyyuditsky.exchangerates.adapters.CurrenciesAdapter
import com.alexeyyuditsky.exchangerates.databinding.FragmentCurrenciesBinding
import com.alexeyyuditsky.exchangerates.utils.codesAndNamesMap
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
        binding = FragmentCurrenciesBinding.bind(view).apply { recyclerView.scrollToPosition(0) }

        setupCurrenciesList()
        setupSearchInput()
    }

    override fun onPause() {
        stopShimmer()
        super.onPause()
    }

    private fun setupCurrenciesList() {
        val adapter = CurrenciesAdapter(viewModel, ::stopShimmer)

        binding.recyclerView.adapter = adapter
        (binding.recyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireActivity(), RecyclerView.VERTICAL))

        observeCurrencies(adapter)
    }

    private fun observeCurrencies(adapter: CurrenciesAdapter) = lifecycleScope.launch {
        viewModel.currenciesFlow.collectLatest { pagingData ->
            adapter.submitData(pagingData)
        }
    }

    private fun stopShimmer() {
        binding.shimmer.stopShimmer()
        binding.shimmer.isGone = true
    }

    private fun setupSearchInput() {
        binding.searchTextInput.searchEditText.addTextChangedListener {
            val string = it.toString()
            val currencyCodesList = mutableListOf<String>()

            codesAndNamesMap.forEach { (key, value) ->
                if (value.contains(string, true) || key.contains(string, true))
                    currencyCodesList.add(key)
            }
            viewModel.setSearchBy(currencyCodesList)
        }
    }

}