package com.example.cloneopenweather.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController


@Composable
fun WeatherNavigation(modifier: Modifier = Modifier) {
    val navigation = rememberNavController()
    NavHost(navController = navigation, startDestination = Login) {

    }
}