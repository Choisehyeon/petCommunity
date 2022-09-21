package com.example.withpet.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val BASE_URL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/"

    val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getInstance() : Retrofit {
        return retrofit
    }
}