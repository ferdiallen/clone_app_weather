package com.example.cloneopenweather.domain.usecase.RetrieveWeatherInfoUseCase

import com.example.cloneopenweather.core.util.WeatherResource
import com.example.cloneopenweather.data.network.weather.WeatherModel
import com.example.cloneopenweather.domain.model.WeatherDomainModel
import com.example.cloneopenweather.domain.model.toWeatherDomainModel
import com.example.cloneopenweather.domain.repository.weather.RetrieveWeather
import retrofit2.HttpException

class RetrieveWeatherUseCase(
    private val service: RetrieveWeather
) {
    suspend fun retrieveWeatherUseCase(
        lat: Double,
        lon: Double,
        appId: String,
        units: String
    ): WeatherResource<WeatherDomainModel> {
        return try {
            val data  = service.retrieveWeatherDetail(
                lat = lat,
                lon = lon,
                appid = appId,
                units = units
            )
            WeatherResource.Success(
                data.toWeatherDomainModel()
            )
        } catch (e: Exception) {
            WeatherResource.Error(e.message ?:"Unknown Error")
        }catch (e: HttpException){
            WeatherResource.Error(e.message ?:"Unknown Error")
        }
    }
}
