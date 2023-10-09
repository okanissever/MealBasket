package com.example.mealbasket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealbasket.model.bringall.Yemekler
import com.example.mealbasket.databinding.ItemMealsBinding
import com.example.mealbasket.util.Constants.IMAGE_URL
import com.example.mealbasket.viewmodel.CartViewModel
import com.example.mealbasket.viewmodel.HomeViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeAdapter @Inject constructor(val viewModel: CartViewModel) : ListAdapter<Yemekler,HomeAdapter.YemekHolder>(NoteDiffCallback) {

    var onItemClick :(Yemekler) -> Unit = {}

    object NoteDiffCallback : DiffUtil.ItemCallback<Yemekler>() {
        override fun areItemsTheSame(oldItem: Yemekler, newItem: Yemekler): Boolean {
            return oldItem.yemek_id == newItem.yemek_id
        }

        override fun areContentsTheSame(oldItem: Yemekler, newItem: Yemekler): Boolean {
            return oldItem == newItem
        }
    }

    class YemekHolder(val binding : ItemMealsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YemekHolder {
        val binding = ItemMealsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return YemekHolder(binding)
    }

    override fun onBindViewHolder(holder: YemekHolder, position: Int) {
        val item = currentList[position]
        val image = IMAGE_URL + item.yemek_resim_adi

        Glide.with(holder.binding.imgMeal).load(image).into(holder.binding.imgMeal)
        holder.binding.foodNameTV.text = item.yemek_adi
        holder.binding.priceTV.text = item.yemek_fiyat +" TL"

        holder.binding.addBtn.setOnClickListener {
            viewModel.addFood(mealName = item.yemek_adi, mealImage = image, mealPrice = item.yemek_fiyat.toInt(), mealQuantity = 1)
            Toast.makeText(it.context,"${item.yemek_adi} added.",Toast.LENGTH_SHORT).show()
        }

        holder.binding.root.setOnClickListener {
            onItemClick(item)
        }
    }

}