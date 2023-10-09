package com.example.mealbasket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealbasket.model.bringall.Yemekler
import com.example.mealbasket.model.bringbasket.Basket
import com.example.mealbasket.repo.MealsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(val repo: MealsRepo) : ViewModel() {

    fun addFood(mealName: String, mealImage: String, mealPrice: Int, mealQuantity: Int){
        viewModelScope.launch {
            repo.addFood(mealName = mealName,mealImage = mealImage,mealPrice = mealPrice,mealQuantity = mealQuantity)
        }
    }

    fun addToFavorites(meal : Yemekler){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertMeal(meal)
            // Eğer UI güncellemesi yapacaksanız, Handler veya runOnUiThread kullanmanız gerekir.
        }
    }

    fun isItemFavorited(itemId: Int): LiveData<Yemekler?> {
        return repo.getMealById(itemId)
    }

    fun deleteFavorites(meal : Yemekler){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteMeal(meal)
        }
    }

}