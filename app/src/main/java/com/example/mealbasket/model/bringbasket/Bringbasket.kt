package com.example.mealbasket.model.bringbasket

import androidx.room.Entity

data class Bringbasket (
    val sepet_yemekler: List<Basket>,
    val success: Int
)
