package com.alexeyyuditsky.exchange_rates.screens.favorite_currencies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.FragmentFavoriteCurrenciesBinding

class FavoriteCurrenciesFragment : Fragment(R.layout.fragment_favorite_currencies) {

    private lateinit var binding: FragmentFavoriteCurrenciesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteCurrenciesBinding.bind(view)
    }

}