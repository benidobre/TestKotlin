package com.example.bdobre.testkotlin.GOTUtils

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by bdobre on 08/09/2017.
 */

interface GOTService {

    @GET("api/books?page=1")
    fun getBooks(@Query("pageSize") pageSize: Int) : Call<List<Book>>

    @GET("api/houses")
    fun getHouses(@Query("page") page: Int,@Query("pageSize") pageSize: Int) : Call<List<House>>

    @GET("api/characters/{ch}")
    fun getCharacter(@Path("ch") ch: Int) : Call<Character>

    companion object Service{
        var sInsance: GOTService? = null

        @Synchronized
        fun Get() : GOTService? {
            if(sInsance == null) {
                sInsance = Retrofit.Builder()
                        .baseUrl("https://www.anapioficeandfire.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(GOTService::class.java)
            }
            return sInsance;
        }

    }
}