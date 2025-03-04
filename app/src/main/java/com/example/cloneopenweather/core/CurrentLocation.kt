package com.example.cloneopenweather.core

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationRequest
import android.os.Looper
import com.example.cloneopenweather.domain.model.LocationDetail
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority


class CurrentLocation {
    private var fusedLocation: FusedLocationProviderClient? = null
    private var callback: LocationCallback? = null
    fun stopLocationUpdate() {
        callback?.let {
            fusedLocation?.removeLocationUpdates(it)
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        context: Context,
        successCallback: (LocationDetail?) -> Unit
    ) {
        fusedLocation = LocationServices.getFusedLocationProviderClient(context)
        val request = com.google.android.gms.location.LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5_000
        )
            .setMinUpdateIntervalMillis(2_000)
            .build()

        callback = object : LocationCallback() {
            override fun onLocationResult(data: LocationResult) {
                super.onLocationResult(data)
                val location = data.locations.firstOrNull()
                if (location == null) {
                    successCallback(null)
                    return
                }
                location.let {
                    successCallback(LocationDetail(it.latitude, it.longitude))
                }
            }
        }

        fusedLocation?.requestLocationUpdates(
            request,
            callback ?: return, Looper.getMainLooper()
        )
    }

}
