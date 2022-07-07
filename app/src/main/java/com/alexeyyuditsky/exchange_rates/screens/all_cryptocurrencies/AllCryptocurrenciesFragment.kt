package com.alexeyyuditsky.exchange_rates.screens.all_cryptocurrencies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.FragmentAllCryptocurrenciesBinding

class AllCryptocurrenciesFragment : Fragment(R.layout.fragment_all_cryptocurrencies) {

    private lateinit var binding: FragmentAllCryptocurrenciesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllCryptocurrenciesBinding.bind(view)
    }

}