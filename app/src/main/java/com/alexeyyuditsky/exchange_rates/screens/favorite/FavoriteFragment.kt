package com.alexeyyuditsky.exchange_rates.screens.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.adapters.FavoritesAdapter
import com.alexeyyuditsky.exchange_rates.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val viewModel by viewModels<FavoriteViewModel>()
    private lateinit var binding: FragmentFavoriteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        setupFavoritesList()
    }

    private fun setupFavoritesList() {
        val adapter = FavoritesAdapter(viewModel)

        binding.recyclerView.adapter = adapter
        (binding.recyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                RecyclerView.VERTICAL
            )
        )

        observeFavorites(adapter)
    }

    private fun observeFavorites(adapter: FavoritesAdapter) = lifecycleScope.launch {
        viewModel.favoriteCurrencies.observe(viewLifecycleOwner) {
            adapter.renderSettings(it)
        }
    }

}