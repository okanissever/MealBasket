package com.example.mealbasket.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mealbasket.model.bringall.Yemekler

@Database(entities = [Yemekler::class], version = 2)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}