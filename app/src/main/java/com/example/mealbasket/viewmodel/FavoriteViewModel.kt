package com.example.mealbasket.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealbasket.model.bringall.Yemekler
import com.example.mealbasket.repo.MealsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(val repo: MealsRepo) : ViewModel() {

    private val favoritesList = MutableLiveData<List<Yemekler>>()

    init {
        getAll()
    }

    fun deleteFavorites(meal : Yemekler){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteMeal(meal)
            getAll()
        }
    }

    fun getAll(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = repo.getAll()
            withContext(Dispatchers.Main) {
                favoritesList.value = list
            }
        }
    }

    fun observeFavoritesList() : MutableLiveData<List<Yemekler>>{
        return favoritesList
    }

}