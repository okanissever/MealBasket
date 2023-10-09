package com.example.mealbasket.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealbasket.model.bringbasket.Basket
import com.example.mealbasket.model.bringbasket.Bringbasket
import com.example.mealbasket.repo.MealsRepo
import com.example.mealbasket.util.Constants.NICKNAME
import com.example.mealbasket.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(val repo: MealsRepo): ViewModel() {

    private val mealList = MutableLiveData<Resource<Bringbasket>>()
    private val _totalPrice = MutableLiveData<Double>() // Toplam fiyatı takip etmek için
    val totalPrice: LiveData<Double> get() = _totalPrice

    init {
        fetchMeals()
    }

    private fun updateTotalPrice(baskets: List<Basket>?) {
        if (!baskets.isNullOrEmpty()) {
            val total = baskets.sumByDouble { (it.yemek_siparis_adet * it.yemek_fiyat).toDouble() }
            _totalPrice.value = total
        } else {
            _totalPrice.value = 0.0
        }
    }

    fun fetchMeals(){
        mealList.value = Resource.Loading()
        viewModelScope.launch {
        try {
            val response = repo.bringBasket(NICKNAME)
            Log.d("CartViewModel", "API Response: ${response.body()}")
            if(response.isSuccessful){
                    response.body()?.let {
                        mealList.value = Resource.Success(it)
                        updateTotalPrice(it.sepet_yemekler) // Mevcut listeye göre toplam fiyatı güncelle
                    }
                }else{
                    mealList.value = Resource.Error(response.message())

            }
        }catch (e : Exception){
            mealList.value = Resource.Error(e.message.toString())
        }
        }
    }

    fun addFood(mealName: String, mealImage: String, mealPrice: Int, mealQuantity: Int){
        viewModelScope.launch {
            repo.addFood(mealName = mealName,mealImage = mealImage,mealPrice = mealPrice,mealQuantity = mealQuantity)
            fetchMeals()
        }
    }

    fun deleteMeal(id : Int){
        viewModelScope.launch {
            try {
                val response = repo.deleteMeal(id, nickname = NICKNAME)
                Log.d("CartViewModel", "API Response: ${response.body()}")
                if (response.isSuccessful) {
                    fetchMeals()
                } else {
                    // handle the error, for example log it or show a message to the user
                    Log.e("CartViewModel", "Failed to delete meal: ${response.message()}")
                }
            } catch (e: Exception) {
                // handle the exception, for example log it or show a message to the user
                Log.e("CartViewModel", "Exception when deleting meal", e)
            }
        }
    }

    fun observeMealList() : LiveData<Resource<Bringbasket>> {
        return mealList
    }
}