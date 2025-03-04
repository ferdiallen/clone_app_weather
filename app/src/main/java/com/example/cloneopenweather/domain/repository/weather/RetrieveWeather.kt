package com.example.cloneopenweather.domain.repository.weather

import com.example.cloneopenweather.core.util.WeatherResource
import com.example.cloneopenweather.data.network.weather.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrieveWeather {
    @GET("/data/2.5/weather")
    suspend fun retrieveWeatherDetail(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("units") units: String,
    ) : WeatherModel
}