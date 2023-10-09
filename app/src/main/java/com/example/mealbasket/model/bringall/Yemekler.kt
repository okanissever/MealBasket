package com.example.mealbasket.model.bringall

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("favorite")
data class Yemekler(
    val yemek_adi: String,
    val yemek_fiyat: String,
    @PrimaryKey
    val yemek_id: String,
    val yemek_resim_adi: String,
    var isFavorite: Boolean = false
) : Serializable