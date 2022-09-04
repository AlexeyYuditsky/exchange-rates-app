package com.alexeyyuditsky.exchangerates.screens.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchangerates.R
import com.alexeyyuditsky.exchangerates.adapters.FavoritesAdapter
import com.alexeyyuditsky.exchangerates.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
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

    private fun stopShimmer() {
        binding.shimmer.stopShimmer()
        binding.shimmer.isGone = true
    }

    private fun setupFavoritesList() {
        val adapter = FavoritesAdapter(viewModel)

        binding.recyclerView.adapter = adapter
        (binding.recyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        observeFavorites(adapter)
    }

    override fun onPause() {
        stopShimmer()
        super.onPause()
    }

    private fun observeFavorites(adapter: FavoritesAdapter) = lifecycleScope.launch {
        viewModel.favoriteCurrencies.collectLatest {
            if (it.isNotEmpty()) stopShimmer()
            adapter.renderSettings(it)
        }
    }

}