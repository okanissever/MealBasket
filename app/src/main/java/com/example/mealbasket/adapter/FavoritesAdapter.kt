package com.example.mealbasket.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.example.mealbasket.databinding.ItemCartBinding
import com.example.mealbasket.databinding.ItemFavoriteBinding
import com.example.mealbasket.model.bringall.Yemekler
import com.example.mealbasket.model.bringbasket.Basket
import com.example.mealbasket.util.Constants
import com.example.mealbasket.viewmodel.FavoriteViewModel
import javax.inject.Singleton

class FavoritesAdapter(val viewModel : FavoriteViewModel) : androidx.recyclerview.widget.ListAdapter<Yemekler,FavoritesAdapter.FavoritesHolder>(NoteDiffCallback) {

    object NoteDiffCallback : DiffUtil.ItemCallback<Yemekler>() {
        override fun areItemsTheSame(oldItem: Yemekler, newItem: Yemekler): Boolean {
            return oldItem.yemek_id == newItem.yemek_id
        }

        override fun areContentsTheSame(oldItem: Yemekler, newItem: Yemekler): Boolean {
            return oldItem == newItem
        }
    }

    class FavoritesHolder(val binding : ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoritesHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesHolder, position: Int) {
        val item = currentList[position]

        Glide.with(holder.binding.imgFood).load(item.yemek_resim_adi).into(holder.binding.imgFood)
        holder.binding.foodNameTV.text = item.yemek_adi
        holder.binding.priceTV.text = item.yemek_fiyat +" TL"

        holder.binding.fakePriceTV.paintFlags = holder.binding.fakePriceTV.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.binding.fakePriceTV.text = (item.yemek_fiyat.toDouble()*110/100).toString() + " TL"

        holder.binding.deleteMeal.setOnClickListener {
            viewModel.deleteFavorites(item)
        }

    }

}