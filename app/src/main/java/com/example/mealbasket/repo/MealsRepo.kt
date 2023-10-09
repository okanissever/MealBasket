package com.example.mealbasket.repo

import androidx.lifecycle.LiveData
import com.example.mealbasket.api.RetrofitApi
import com.example.mealbasket.model.bringall.Yemekler
import com.example.mealbasket.room.MealDatabase
import com.example.mealbasket.util.Constants
import javax.inject.Inject

class MealsRepo @Inject constructor(
    val api : RetrofitApi,
    val db : MealDatabase){

    suspend fun getAllMeals() = api.getAllMeals()

    suspend fun addFood(mealName : String,mealImage : String,mealPrice : Int,mealQuantity : Int,nickname : String = Constants.NICKNAME)
    = api.addFood(mealName = mealName,mealImage = mealImage,mealPrice = mealPrice,mealQuantity = mealQuantity,nickname = Constants.NICKNAME )

    suspend fun bringBasket(nickname:String) = api.bringBasket(nickname = nickname)

    suspend fun deleteMeal(id : Int,nickname: String) = api.deleteMeal(id,nickname)

    fun insertMeal(meal : Yemekler) = db.mealDao().insertMeal(meal)

    fun deleteMeal(meal : Yemekler) = db.mealDao().deleteMeal(meal)

    fun getAll() = db.mealDao().getAll()

    fun updateMeal(meal:Yemekler) = db.mealDao().updateMeal(meal)

    fun getMealById(id: Int): LiveData<Yemekler?> = db.mealDao().isItemFavorited(id)

}