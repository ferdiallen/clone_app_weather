package com.example.cloneopenweather.core

import com.example.cloneopenweather.domain.repository.weather.RetrieveWeather
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val json = Json {
    ignoreUnknownKeys = true
    this.explicitNulls = false
}

val retrofitInstance by lazy<Retrofit> {
    val res = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            json.asConverterFactory(
                MediaType.parse("application/json; charset=UTF8")!!
            )
        ).build()
    res
}

val weatherService: RetrieveWeather = retrofitInstance.create<RetrieveWeather>(RetrieveWeather::class.java)