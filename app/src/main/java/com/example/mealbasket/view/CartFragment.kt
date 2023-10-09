package com.example.mealbasket.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mealbasket.R
import com.example.mealbasket.adapter.CartAdapter
import com.example.mealbasket.databinding.FragmentCartBinding
import com.example.mealbasket.util.Resource
import com.example.mealbasket.util.TotalPriceListener
import com.example.mealbasket.util.viewBinding
import com.example.mealbasket.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)
    private val viewModel : CartViewModel by activityViewModels()

    private lateinit var adapter : CartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CartAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        observeData()
        observeTotalPrice()

        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_paymentFragment)
        }

        adapter.listener = object : TotalPriceListener {
            override fun onPriceUpdated(totalPrice: Double) {
                binding.priceTV.text = "$totalPrice TL"
            }
        }
    }

    private fun observeData(){
        viewModel.observeMealList().observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Success->{
                    response.data?.let {
                        hideProgresBar()
                        adapter.submitList(it.sepet_yemekler)
                        if (it.sepet_yemekler.isEmpty()) {
                            binding.imgEmpty.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.imgError.visibility = View.GONE
                        } else {
                            binding.imgEmpty.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.linearLayout.visibility = View.VISIBLE
                        }
                    }
                }
                is Resource.Loading->{
                    showProgresBar()
                }
                is Resource.Error->{
                    Log.e("Cart Fragment",response.message.toString())
                    hideProgresBar()
                    binding.recyclerView.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.imgError.visibility = View.VISIBLE
                    binding.linearLayout.visibility = View.GONE

                    if(response.message.toString() == "End of input at line 6 column 1 path $"){
                        binding.recyclerView.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        binding.imgError.visibility = View.GONE
                        binding.imgEmpty.visibility = View.VISIBLE
                        binding.linearLayout.visibility = View.GONE
                    }
                }

            }
        }

    }

    private fun observeTotalPrice() {
        viewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.priceTV.text = "$totalPrice TL"
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchMeals()
    }


    private fun showProgresBar(){
        binding.imgError.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgresBar(){
        binding.apply {
            progressBar.visibility = View.GONE
            binding.imgError.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

}