package com.example.cloneopenweather.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    var isGranted by mutableStateOf(false)
        private set

    fun setGrantedState(state: Boolean)  = isGranted.let {
        isGranted = state
    }
}