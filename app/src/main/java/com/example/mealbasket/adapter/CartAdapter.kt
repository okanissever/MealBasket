package com.example.mealbasket.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealbasket.databinding.ItemCartBinding
import com.example.mealbasket.model.bringbasket.Basket
import com.example.mealbasket.util.TotalPriceListener
import com.example.mealbasket.viewmodel.CartViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartAdapter @Inject constructor(val viewModel: CartViewModel): ListAdapter<Basket,CartAdapter.CartHolder>(NoteDiffCallback) {

    var listener: TotalPriceListener? = null

    object NoteDiffCallback : DiffUtil.ItemCallback<Basket>() {
        override fun areItemsTheSame(oldItem: Basket, newItem: Basket): Boolean {
            return oldItem.sepet_yemek_id == newItem.sepet_yemek_id
        }

        override fun areContentsTheSame(oldItem: Basket, newItem: Basket): Boolean {
            return oldItem == newItem
        }
    }

    class CartHolder(val binding : ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartHolder(binding)
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        val item = currentList[position]

        holder.binding.apply {
            foodNameTV.text = item.yemek_adi
            priceTV.text = item.yemek_fiyat.toString() + " TL"
            pieceTV.text = "Quantity :" + item.yemek_siparis_adet.toString()
            fakePriceTV.paintFlags = fakePriceTV.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            fakePriceTV.text = (item.yemek_fiyat.toDouble()*110/100).toString() + " TL"

            totalPriceTV.text =(item.yemek_siparis_adet * item.yemek_fiyat).toString() + " TL"

            deleteMeal.setOnClickListener {
                viewModel.deleteMeal(item.sepet_yemek_id)
                Toast.makeText(it.context,"${item.yemek_adi} deleted.",Toast.LENGTH_SHORT).show()
                updateTotalPrice()
            }

        }

        Glide.with(holder.binding.imgFood).load(item.yemek_resim_adi).into(holder.binding.imgFood)
    }

    private fun updateTotalPrice() {
        val total = currentList.sumByDouble { (it.yemek_siparis_adet * it.yemek_fiyat).toDouble() }
        listener?.onPriceUpdated(total)
    }

}
