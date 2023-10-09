package com.example.mealbasket.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mealbasket.R
import com.example.mealbasket.adapter.CartAdapter
import com.example.mealbasket.adapter.FavoritesAdapter
import com.example.mealbasket.databinding.FragmentDetailsBinding
import com.example.mealbasket.databinding.FragmentFavoriteBinding
import com.example.mealbasket.util.viewBinding
import com.example.mealbasket.viewmodel.DetailsViewModel
import com.example.mealbasket.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val binding by viewBinding(FragmentFavoriteBinding::bind)
    private val viewModel : FavoriteViewModel by viewModels()

    lateinit var adapter : FavoritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoritesAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        observeData()
    }

    private fun observeData(){
        viewModel.observeFavoritesList().observe(viewLifecycleOwner){list->
            list?.let {
                adapter.submitList(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getAll()
        observeData()

    }

}