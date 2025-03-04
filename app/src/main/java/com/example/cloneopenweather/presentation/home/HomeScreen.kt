package com.example.cloneopenweather.presentation.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cloneopenweather.core.openMenuAppSetting
import com.example.cloneopenweather.ui.theme.BackgroundColor
import com.example.cloneopenweather.ui.theme.ColdColorIndicator
import com.example.cloneopenweather.ui.theme.TempDetailColor
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.cloneopenweather.core.util.WeatherResource
import com.example.cloneopenweather.domain.model.WeatherDomainModel
import com.example.cloneopenweather.ui.theme.HeatColorIndicator

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentLocationData by viewModel.latestLocation.collectAsStateWithLifecycle()
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val isAllGranted = it.all {
                it.value
            }
            if (isAllGranted) {
                viewModel.setGrantedState(true)
                viewModel.retrieveLatestLocation(context)
            } else {
                viewModel.setGrantedState(false)
            }
        }
    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
    Column(
        modifier.background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (currentLocationData) {
            is WeatherResource.Success -> {
                with((currentLocationData as WeatherResource.Success<WeatherDomainModel>).data) {
                    Text(buildAnnotatedString {
                        withStyle(SpanStyle()) {
                            append("${location},")
                        }
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append(" $countryName")
                        }
                    }, color = Color.White, fontSize = 20.sp)
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 64.sp,
                                    baselineShift = BaselineShift.Subscript,
                                    color = if (tempValue >= 40) HeatColorIndicator else ColdColorIndicator
                                )
                            ) {
                                append("$tempValue")
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 42.sp,
                                    baselineShift = BaselineShift.Superscript
                                )
                            ) {
                                append("°C")
                            }
                        }, color = ColdColorIndicator,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        "$description",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(0.9F), colors = CardDefaults.cardColors(
                            containerColor = TempDetailColor
                        ),
                        shape = CircleShape
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier.weight(1F),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Wind", fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White, maxLines = 1
                                )
                                Text(
                                    "$windSpeed km/h", fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White, maxLines = 1
                                )
                            }

                            VerticalDivider(
                                modifier = Modifier
                                    .height(100.dp)
                                    .padding(vertical = 12.dp),
                                color = Color.Gray,
                                thickness = 0.5.dp
                            )
                            Column(
                                modifier = Modifier.weight(1F),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Temp", fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                                Text(
                                    "${this@with.feelsLike}", fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White, maxLines = 1
                                )
                            }

                            VerticalDivider(
                                modifier = Modifier
                                    .height(100.dp)
                                    .padding(vertical = 12.dp),
                                color = Color.Gray,
                                thickness = 0.5.dp
                            )
                            Column(
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(end = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Humidity", fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White, maxLines = 1
                                )
                                Text(
                                    "$humidity", fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White, maxLines = 1
                                )
                            }

                        }
                    }
                }
            }

            is WeatherResource.Error -> {
                Text(
                    "Unable to load data ${(currentLocationData as WeatherResource.Error).message}",
                    fontSize = 20.sp, color = Color.White
                )
            }

            WeatherResource.Idle -> {}
            WeatherResource.Loading -> {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}