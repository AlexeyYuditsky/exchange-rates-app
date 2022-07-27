package com.alexeyyuditsky.exchange_rates.screens.cryptocurrencies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.FragmentCryptocurrenciesBinding

class CryptocurrenciesFragment : Fragment(R.layout.fragment_cryptocurrencies) {

    private lateinit var binding: FragmentCryptocurrenciesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCryptocurrenciesBinding.bind(view)
    }

}