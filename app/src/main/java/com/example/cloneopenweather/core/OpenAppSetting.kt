package com.example.cloneopenweather.core

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.net.toUri

fun openMenuAppSetting(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        data = "package:${context.packageName}".toUri()
    }
    context.startActivity(intent)
}