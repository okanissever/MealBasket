package com.example.mealbasket.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealbasket.model.bringall.Meal
import com.example.mealbasket.util.Resource
import com.example.mealbasket.repo.MealsRepo
import com.example.mealbasket.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repo: MealsRepo) : ViewModel() {

    private val mealList = MutableLiveData<Resource<Meal>>()

    init {
        fetchMeals()
    }

    private fun fetchMeals(){
        mealList.value = Resource.Loading()
        try {
            viewModelScope.launch {
                val response = repo.getAllMeals()
                if(response.isSuccessful){
                    response.body()?.let {
                        mealList.value = Resource.Success(it)
                    }
                }else{
                    mealList.value = Resource.Error(response.message())
                }

            }
        }catch (e : Exception){
            mealList.value = Resource.Error(e.message.toString())
        }
    }

    fun observeMealList() :MutableLiveData<Resource<Meal>>{
        return mealList
    }



}