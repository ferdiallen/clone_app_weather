package com.example.cloneopenweather.core

import android.content.Context
import android.content.Intent
import android.provider.Settings

fun openMenuAppSetting(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    context.startActivity(intent)
}