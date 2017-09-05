package com.example.bdobre.testkotlin.bikeUtils

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by bdobre on 05/09/2017.
 */

interface BikeService {

    @GET("/v2/networks")
    fun getData() : Call<BikeResponse>

    companion object Service{
        var sInsance: BikeService? = null

        @Synchronized
         fun Get() : BikeService? {
            if(sInsance == null) {
                sInsance = Retrofit.Builder()
                        .baseUrl("http://api.citybik.es/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(BikeService::class.java)
            }
            return sInsance;
        }

    }
}