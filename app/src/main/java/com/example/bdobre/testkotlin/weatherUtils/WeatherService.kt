package com.example.bdobre.testkotlin.weatherUtils

import com.example.bdobre.testkotlin.bikeUtils.BikeResponse
import com.example.bdobre.testkotlin.bikeUtils.BikeService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by bdobre on 07/09/2017.
 */

interface WeatherService {

    @GET("/api/location/search/")
    fun getNearCities(@Query("lattlong") latlong :  String) : Call<List<WeatherLocation>>

    @GET("/api/location/{id}")
    fun getWeather(@Path("id") id : Int) : Call<WeatherResponse>

    @GET("/api/location/search/")
    fun getCities(@Query("query") query : String) : Call<List<CitySearch>>

    companion object Service{
        var sInsance: WeatherService? = null

        @Synchronized
        fun Get() : WeatherService? {
            if(sInsance == null) {
                sInsance = Retrofit.Builder()
                        .baseUrl("https://www.metaweather.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(WeatherService::class.java)
            }
            return sInsance;
        }

    }
}