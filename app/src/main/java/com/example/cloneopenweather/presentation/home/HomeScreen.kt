package com.example.cloneopenweather.presentation.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
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
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.currentStateAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cloneopenweather.core.openMenuAppSetting
import com.example.cloneopenweather.core.util.WeatherResource
import com.example.cloneopenweather.domain.model.WeatherDomainModel
import com.example.cloneopenweather.ui.theme.ColdColorIndicator
import com.example.cloneopenweather.ui.theme.HeatColorIndicator
import com.example.cloneopenweather.ui.theme.TempDetailColor

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentLocationData by viewModel.latestLocation.collectAsStateWithLifecycle()
    val isGranted by remember {
        derivedStateOf {
            viewModel.isGranted
        }
    }
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
    val lifecycle = LocalLifecycleOwner.current
    val lifecycleState by lifecycle.lifecycle.currentStateAsState()
    LaunchedEffect(lifecycleState) {
        if (lifecycleState == Lifecycle.State.RESUMED) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )

        }
    }
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Crossfade(isGranted) {
            if (it) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
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
                                    description,
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(32.dp))
                                Card(
                                    modifier = Modifier.fillMaxWidth(0.9F),
                                    colors = CardDefaults.cardColors(
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
                                                stringResource(com.example.cloneopenweather.R.string.wind),
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.White,
                                                maxLines = 1
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
                                                stringResource(com.example.cloneopenweather.R.string.temp),
                                                fontSize = 20.sp,
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
                                                stringResource(com.example.cloneopenweather.R.string.humidity),
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.White,
                                                maxLines = 1
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
                                stringResource(
                                    com.example.cloneopenweather.R.string.unable_to_load_data,
                                    ""
                                ),
                                fontSize = 20.sp, color = Color.White
                            )
                        }

                        WeatherResource.Idle -> {}
                        WeatherResource.Loading -> {
                            CircularProgressIndicator(color = Color.White)
                        }
                    }

                }

            } else if (!it && currentLocationData is WeatherResource.Success) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(com.example.cloneopenweather.R.string.location_permission_is_not_granted),
                        color = Color.White, fontSize = 20.sp
                    )
                    Button(onClick = {
                        openMenuAppSetting(context)
                    }) {
                        Text("Open App Settings", color = Color.White)
                    }

                }
            }
        }
    }
}