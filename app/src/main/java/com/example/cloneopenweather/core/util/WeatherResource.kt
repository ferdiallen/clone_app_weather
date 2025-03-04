package com.example.cloneopenweather.core.util

sealed class WeatherResource<out T> {
    data class Success<T>(val data: T) : WeatherResource<T>()
    data class Error<T>(val message: String) : WeatherResource<T>()
    object Loading : WeatherResource<Nothing>()
    object Idle : WeatherResource<Nothing>()

}