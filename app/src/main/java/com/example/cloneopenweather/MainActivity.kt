package com.example.cloneopenweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cloneopenweather.core.navigation.WeatherNavigation
import com.example.cloneopenweather.presentation.home.HomeScreen
import com.example.cloneopenweather.presentation.login.LoginScreen
import com.example.cloneopenweather.ui.theme.CloneOpenWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CloneOpenWeatherTheme {
                WeatherNavigation(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

