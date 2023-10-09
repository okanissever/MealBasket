package com.example.mealbasket.view

import android.graphics.Color
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mealbasket.R
import com.example.mealbasket.databinding.FragmentDetailsBinding
import com.example.mealbasket.model.bringall.Meal
import com.example.mealbasket.model.bringall.Yemekler
import com.example.mealbasket.model.bringbasket.Basket
import com.example.mealbasket.util.Constants.IMAGE_URL
import com.example.mealbasket.util.viewBinding
import com.example.mealbasket.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val viewModel : DetailsViewModel by viewModels()
    private val args : DetailsFragmentArgs by navArgs()

    private var quantity = 1

    private var isRed = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val image = IMAGE_URL + args.meal.yemek_resim_adi

        binding.totalPriceTV.text = args.meal.yemek_fiyat + " TL"
        binding.priceTV.text = args.meal.yemek_fiyat + " TL"
        Glide.with(binding.imgFood).load(image).into(binding.imgFood)
        binding.nameTV.text = args.meal.yemek_adi

        binding.addToFavorite.setOnClickListener {
            if(isRed){
                viewModel.deleteFavorites(Yemekler(yemek_id = args.meal.yemek_id, yemek_adi = args.meal.yemek_adi, yemek_fiyat = args.meal.yemek_fiyat, yemek_resim_adi = image))
                Toast.makeText(it.context,"${args.meal.yemek_adi} deleted to favorites.",Toast.LENGTH_SHORT).show()
                isRed = false
                binding.addToFavorite.setColorFilter(Color.GRAY)
            }
            else{
                viewModel.addToFavorites(Yemekler(yemek_id = args.meal.yemek_id, yemek_adi = args.meal.yemek_adi, yemek_fiyat = args.meal.yemek_fiyat, yemek_resim_adi = image))
                Toast.makeText(it.context,"${args.meal.yemek_adi} added to favorites.",Toast.LENGTH_SHORT).show()
                binding.addToFavorite.setColorFilter(ContextCompat.getColor(binding.addToFavorite.context, R.color.red))
                isRed = true
            }

        }

        binding.addIV.setOnClickListener {
            quantity++
            binding.pieceTV.text = quantity.toString()
            binding.totalPriceTV.text = (quantity * args.meal.yemek_fiyat.toInt()).toString() + " TL"
        }

        binding.minusIV.setOnClickListener {
            quantity--
            if(quantity == 0){
                quantity = 1
                Toast.makeText(requireContext(),"Quantity can't be 0",Toast.LENGTH_SHORT).show()
            }
            binding.pieceTV.text = quantity.toString()
            binding.totalPriceTV.text = (quantity * args.meal.yemek_fiyat.toInt()).toString() + " TL"
        }

        binding.addBtn.setOnClickListener {
            viewModel.addFood(mealName = args.meal.yemek_adi, mealImage = image, mealPrice = args.meal.yemek_fiyat.toInt(), mealQuantity = quantity)
            Toast.makeText(it.context,"${args.meal.yemek_adi} added",Toast.LENGTH_SHORT).show()
        }

        observeData(args.meal.yemek_id.toInt())

    }

    private fun observeData(id:Int){
        viewModel.isItemFavorited(id).observe(viewLifecycleOwner){favoriteItem ->
            if (favoriteItem != null) {
                binding.addToFavorite.setColorFilter(Color.RED)
            } else {
                binding.addToFavorite.setColorFilter(Color.GRAY)
            }
        }
    }

}