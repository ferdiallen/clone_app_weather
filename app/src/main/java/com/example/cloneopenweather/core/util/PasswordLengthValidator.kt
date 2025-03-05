package com.example.cloneopenweather.core.util

fun String.passwordLengthValidator(): Boolean {
    return this.length >= 8
}