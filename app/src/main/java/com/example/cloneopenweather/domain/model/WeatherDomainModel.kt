package com.example.cloneopenweather.domain.model

import androidx.compose.ui.util.fastRoundToInt
import com.example.cloneopenweather.core.CountryName
import com.example.cloneopenweather.data.network.weather.WeatherModel
import kotlinx.serialization.Serializable
import java.io.Serial
import kotlin.math.roundToInt

@Serializable
data class WeatherDomainModel(
    val tempValue: Int,
    val humidity: Int,
    val windSpeed: Double,
    val countryName: String,
    val location: String,
    val description: String,
    val feelsLike:Int
)

fun WeatherModel.toWeatherDomainModel(): WeatherDomainModel {
    return WeatherDomainModel(
        tempValue = this.main?.temp?.roundToInt() ?: 0,
        humidity = this.main?.humidity ?: 0,
        windSpeed = this.wind?.speed ?: 0.0,
        countryName = CountryName.getCountryName(this.sys?.country ?: ""),
        location = this.name ?: "",
        description = this.weather?.firstOrNull()?.main ?: "",
        feelsLike = this.main?.feelsLike?.fastRoundToInt() ?: 0
    )
}