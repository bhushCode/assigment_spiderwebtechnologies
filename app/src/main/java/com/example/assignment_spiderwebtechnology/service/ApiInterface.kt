package com.example.assignment_spiderwebtechnology.service

import com.example.assignment_spiderwebtechnology.data.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
@GET("forecast.json?")
fun getWeather(@Query("key")key:String,
               @Query("q")q:String,
               @Query("days")days:Int,
               @Query("aqi")aqi:String,
               @Query("alerts")alerts:String,
):Call<WeatherData>





}