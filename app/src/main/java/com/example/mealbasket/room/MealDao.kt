package com.example.mealbasket.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mealbasket.model.bringall.Yemekler

@Dao
interface MealDao {

    @Query("SELECT * From favorite")
    fun getAll(): List<Yemekler>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(meal: Yemekler)

    @Delete
    fun deleteMeal(meal : Yemekler)

    @Update
    fun updateMeal(meal: Yemekler)

    @Query("SELECT * FROM favorite WHERE yemek_id = :itemId LIMIT 1")
    fun isItemFavorited(itemId: Int): LiveData<Yemekler?>

}