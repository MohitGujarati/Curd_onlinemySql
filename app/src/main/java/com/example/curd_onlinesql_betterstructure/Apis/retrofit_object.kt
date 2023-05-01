package com.example.curd_onlinesql_betterstructure.Apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object retrofit_object {


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://mohitgapp.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getapimethod by lazy {
        retrofit.create(api_interface::class.java)
    }


}