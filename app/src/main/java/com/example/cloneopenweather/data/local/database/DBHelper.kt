package com.example.cloneopenweather.data.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "weather.db"
private const val VERSION = 1
const val USERNAME = "username"
 const val PASSWORD = "password"

class DBHelper(
    context: Context,
    factory: SQLiteDatabase.CursorFactory? = null
) : SQLiteOpenHelper(context, DATABASE_NAME, factory, VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
            CREATE TABLE Login(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT NOT NULL,
            password TEXT NOT NULL
            )
        """.trimIndent())
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        p1: Int,
        p2: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $DATABASE_NAME")
        onCreate(db)
    }

}