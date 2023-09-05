package com.example.calatour.apis

import com.example.calatour.models.BreweryByCity
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

public interface BreweryAPI {

    @GET("/breweries")
     fun getBreweriesByCity(@Query("by_city") cityName: String) : Call<List<BreweryByCity>>


    companion object{
        fun createApi():BreweryAPI{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openbrewerydb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create (BreweryAPI::class.java)
        }
    }
}