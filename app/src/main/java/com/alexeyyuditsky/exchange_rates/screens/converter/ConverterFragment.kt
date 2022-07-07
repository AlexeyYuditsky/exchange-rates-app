package com.alexeyyuditsky.exchange_rates.screens.converter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.FragmentConverterBinding

class ConverterFragment : Fragment(R.layout.fragment_converter) {

    private lateinit var binding: FragmentConverterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConverterBinding.bind(view)
    }

}