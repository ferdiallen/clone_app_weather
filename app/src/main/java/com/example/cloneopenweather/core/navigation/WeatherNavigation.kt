package com.example.cloneopenweather.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cloneopenweather.presentation.home.HomeScreen
import com.example.cloneopenweather.presentation.login.LoginScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.cloneopenweather.core.bottomIconList
import com.example.cloneopenweather.presentation.profile.ProfileScreen
import com.example.cloneopenweather.ui.theme.BackgroundColor
import com.example.cloneopenweather.ui.theme.TempDetailColor


@Composable
fun WeatherNavigation(modifier: Modifier = Modifier) {
    val navigation = rememberNavController()
    val currentStack by navigation.currentBackStackEntryAsState()
    val showBottomBar by remember {
        derivedStateOf {
            currentStack?.destination?.route?.contains("Home") == true ||
                    currentStack?.destination?.route?.contains("Profile") == true
        }
    }
    var savedIndex = rememberSaveable {
        mutableStateOf<Int?>(null)
    }
    Scaffold(modifier, bottomBar = {
        AnimatedVisibility(
            visible = showBottomBar,
            enter = slideInVertically(tween(400), initialOffsetY = {
                it + 200
            }),
            exit = slideOutVertically(tween(400), targetOffsetY = {
                it + 200
            })
        ) {
            BottomAppBar(containerColor = TempDetailColor) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    bottomIconList.forEachIndexed { index, data ->
                        BottomBarItem(
                            icon = data.second,
                            title = data.first,
                            onClick = {
                                if (currentStack?.destination?.route?.contains(data.first) == true) {
                                    return@BottomBarItem
                                }
                                if (index == 0) {
                                    navigation.navigate(Home) {
                                        popUpTo<Home>() {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                } else {
                                    navigation.navigate(
                                        Profile
                                    ) {
                                        launchSingleTop = true
                                        popUpTo<Home>() {

                                        }
                                    }
                                }
                            },
                            currentSelected = currentStack?.destination?.route?.contains(data.first) == true
                        )
                    }

                }
            }
        }
    }) { padding ->
        NavHost(
            modifier = modifier.background(BackgroundColor),
            navController = navigation,
            startDestination = Login
        ) {
            composable<Login> {
                LoginScreen(
                    modifier = Modifier.fillMaxSize(),
                    onNavigateToHomeScreen = {
                        savedIndex.value = it
                        navigation.navigate(Home) {
                            launchSingleTop = true
                            popUpTo<Login>() {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<Home> {
                HomeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = padding.calculateBottomPadding())
                )
            }
            composable<Profile> {
                ProfileScreen(
                    modifier = Modifier.fillMaxSize(),
                    index = savedIndex.value ?: return@composable
                )
            }
        }

    }
}

@Composable
fun RowScope.BottomBarItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    currentSelected: Boolean = false
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onClick) {
            Icon(
                icon,
                contentDescription = "",
                tint = if (currentSelected) Color.White else Color.LightGray,
                modifier = Modifier.size(35.dp)
            )
        }
        Text(title, color = if (currentSelected) Color.White else Color.LightGray, maxLines = 1)
    }
}