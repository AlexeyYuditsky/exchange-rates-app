package com.alexeyyuditsky.exchange_rates.screens.converter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
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
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        observeConverter(adapter)
        observeRecyclerView()
    }

    private fun observeRecyclerView() = binding.recyclerView.setOnScrollChangeListener { _, _, _, _, oldScrollY ->
        if (oldScrollY != 0) hideKeyboard()
    }

    private fun hideKeyboard() {
        (requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(binding.recyclerView.windowToken, 0)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeConverter(adapter: ConverterAdapter) = lifecycleScope.launch {
        viewModel.converterCurrencies.observe(viewLifecycleOwner) {
            adapter.currencies = it
        }
    }

}