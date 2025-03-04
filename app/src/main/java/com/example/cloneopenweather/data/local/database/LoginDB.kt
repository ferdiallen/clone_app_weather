package com.example.cloneopenweather.data.local.database

import android.content.ContentValues
import android.content.Context

class LoginDB(
    context: Context
) {
    private val db = DBHelper(context)

    fun insertLoginValue(username: String, password: String) {
        val sql = db.writableDatabase
        val values = ContentValues().apply {
            put(USERNAME, username)
            put(PASSWORD, password)
        }
        sql.use {
            it.insert(DATABASE_NAME, null, values)
            it.close()
        }
    }
}