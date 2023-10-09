package com.example.mealbasket.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mealbasket.util.Resource
import com.example.mealbasket.util.viewBinding
import com.example.mealbasket.R
import com.example.mealbasket.adapter.HomeAdapter
import com.example.mealbasket.databinding.FragmentHomeBinding
import com.example.mealbasket.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel : HomeViewModel by viewModels()

    @Inject
    lateinit var adapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    private fun observeData(){
        viewModel.observeMealList().observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Success->{
                    response.data?.let { meal ->
                        hideProgresBar()
                        adapter.submitList(meal.yemekler)
                        binding.rvMeals.adapter = adapter
                        adapter.onItemClick = {
                            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)
                            val navController = findNavController()
                            if (navController.currentDestination?.id == R.id.homeFragment) {
                                navController.navigate(action)
                            }
                        }
                    }
                }
                is Resource.Loading->{
                    showProgresBar()
                }
                is Resource.Error->{
                    Log.e("Home Fragment",response.message.toString())
                    hideProgresBar()
                    binding.rvMeals.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.imgError.visibility = View.VISIBLE
                }

                else -> {}
            }
        }
    }

    private fun showProgresBar(){
        binding.imgError.visibility = View.GONE
        binding.rvMeals.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgresBar(){
        binding.apply {
            progressBar.visibility = View.GONE
            binding.imgError.visibility = View.GONE
            binding.rvMeals.visibility = View.VISIBLE
        }
    }

}


