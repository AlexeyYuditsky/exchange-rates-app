package com.alexeyyuditsky.exchange_rates.screens.all_currencies

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.adapters.CurrenciesAdapter
import com.alexeyyuditsky.exchange_rates.databinding.FragmentAllCurrenciesBinding
import com.alexeyyuditsky.exchange_rates.utils.log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AllCurrenciesFragment : Fragment(R.layout.fragment_all_currencies) {

    private val viewModel by viewModels<AllCurrenciesViewModel>()
    private lateinit var binding: FragmentAllCurrenciesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllCurrenciesBinding.bind(view)

        setupCurrenciesList()
    }

    private fun setupCurrenciesList() {
        val adapter = CurrenciesAdapter()

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

}