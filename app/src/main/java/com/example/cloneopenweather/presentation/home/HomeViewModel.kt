package com.example.cloneopenweather.presentation.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloneopenweather.core.APIKey
import com.example.cloneopenweather.core.CurrentLocation
import com.example.cloneopenweather.core.util.WeatherResource
import com.example.cloneopenweather.core.weatherService
import com.example.cloneopenweather.domain.model.LocationDetail
import com.example.cloneopenweather.domain.model.WeatherDomainModel
import com.example.cloneopenweather.domain.usecase.RetrieveWeatherInfoUseCase.RetrieveWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var isGranted by mutableStateOf(false)
        private set
    private val location = CurrentLocation()
    private val weatherUseCase = RetrieveWeatherUseCase(weatherService)
    private val _latestLocationValue =
        MutableStateFlow<WeatherResource<WeatherDomainModel>>(WeatherResource.Idle)
    val latestLocation = _latestLocationValue.asStateFlow()

    private fun retrieveWeatherData(lat: Double, lon: Double) =
        viewModelScope.launch(Dispatchers.IO) {
            val weather = weatherUseCase.retrieveWeatherUseCase(
                lat = lat,
                lon = lon,
                appId = APIKey.key,
                units = "metric"
            )
            _latestLocationValue.update {
                weather
            }
        }

    fun retrieveLatestLocation(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        _latestLocationValue.update {
            WeatherResource.Loading
        }
        location.getCurrentLocation(context) {
            it?.let { data ->
                retrieveWeatherData(data.lat, data.long)
                return@let
            }

        }
    }

    fun setGrantedState(state: Boolean) = isGranted.let {
        isGranted = state
    }

    override fun onCleared() {
        super.onCleared()
        location.stopLocationUpdate()
    }
}