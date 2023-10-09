package com.example.mealbasket.api

import com.example.mealbasket.model.addmeal.CrudAnswer
import com.example.mealbasket.model.bringall.Meal
import com.example.mealbasket.model.bringbasket.Bringbasket
import com.example.mealbasket.util.Constants.NICKNAME
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitApi {

    @GET("tumYemekleriGetir.php")
    suspend fun getAllMeals() : Response<Meal>

    @POST("sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addFood(@Field("yemek_adi") mealName : String,
                        @Field("yemek_resim_adi") mealImage : String,
                        @Field("yemek_fiyat") mealPrice : Int,
                        @Field("yemek_siparis_adet") mealQuantity : Int,
                        @Field("kullanici_adi") nickname : String = NICKNAME) : CrudAnswer

    @POST("sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun bringBasket(@Field("kullanici_adi") nickname: String) : Response<Bringbasket>

    @POST("sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteMeal(@Field("sepet_yemek_id") id : Int,
                           @Field("kullanici_adi") nickname: String) :  Response<CrudAnswer>




}