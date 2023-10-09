package com.example.mealbasket.di

import android.content.Context
import androidx.room.Room
import com.example.mealbasket.adapter.CartAdapter
import com.example.mealbasket.adapter.HomeAdapter
import com.example.mealbasket.util.Constants.BASE_URL
import com.example.mealbasket.api.RetrofitApi
import com.example.mealbasket.model.bringall.Meal
import com.example.mealbasket.repo.MealsRepo
import com.example.mealbasket.room.MealDatabase
import com.example.mealbasket.viewmodel.CartViewModel
import com.example.mealbasket.viewmodel.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun injectRetrofitApi() : RetrofitApi{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build().create(RetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun injectMealsRepo(api : RetrofitApi,db : MealDatabase) : MealsRepo{
        return MealsRepo(api,db)
    }


    @Singleton
    @Provides
    fun injectHomeViewModel(repo : MealsRepo) : HomeViewModel{
        return HomeViewModel(repo)
    }

    @Singleton
    @Provides
    fun injectCartViewModel(repo : MealsRepo) : CartViewModel{
        return CartViewModel(repo)
    }

    @Provides
    @Singleton
    fun provideHomeAdapter(viewModel: CartViewModel): HomeAdapter = HomeAdapter(viewModel)

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(context,MealDatabase::class.java,"mealDb").fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun injectDao(database: MealDatabase) = database.mealDao()

}