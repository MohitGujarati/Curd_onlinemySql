package com.example.curd_onlinesql_betterstructure.Apis

import com.example.curd_onlinesql_betterstructure.Model.mymodel
import com.example.curd_onlinesql_betterstructure.Model.update_method
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface api_interface
{


    //post data
    @FormUrlEncoded
    @POST("complain.php")
    fun complain(
        @Field("name") name: String,
        @Field("complains") complains: String,
        @Field("flatno") flatno: String,
        @Field("email") email: String,

        ): Call<List<mymodel>>


    //get all data complains
    @GET("subject.php")
    fun subject(): Call<List<mymodel>>


    @GET("Curdpractice.php")
    fun user_complains(
    ): Call<List<mymodel>>

    //delete data using his id
    @FormUrlEncoded
    @POST("delete_complain.php")
    fun delete_complain(
        @Field("id") id: Int
    ): Call<List<mymodel>>


    //update using his id we are 
    //referring to another table in ourphp code
    @FormUrlEncoded
    @POST("user_complain_update.php")
    fun user_complains_update(
        @Field("complains") complains: String,
        @Field("id") id: Int
    ): Call<List<update_method>>


}
