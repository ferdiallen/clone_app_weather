package com.example.cloneopenweather.data.local.database

import android.content.ContentValues
import com.example.cloneopenweather.domain.model.UserModel

fun insertLoginValue(db: DBHelper, username: String, password: String): Long {
    val sql = db.writableDatabase
    val values = ContentValues().apply {
        put(USERNAME, username)
        put(PASSWORD, password)
    }
    return sql.use {
        val cursor = it.rawQuery(
            "SELECT * FROM Login where username = ? and password = ?",
            arrayOf(username, password)
        )
        while (cursor.moveToNext()) {
            cursor.getString(1)
            cursor.getString(2)
            return -2L
            break
        }
        cursor.close()
        it.insert("Login", null, values)
    }
}

fun retrieveValueLogin(db: DBHelper, username: String, password: String): Pair<Boolean, Int>? {
    var valueData: Pair<Boolean, Int>? = null
    db.readableDatabase.use {
        val cursor = it.rawQuery(
            "SELECT * FROM Login where username = ? and password = ?",
            arrayOf(username, password)
        )
        while (cursor.moveToNext()) {
            valueData = Pair(true, cursor.getInt(0))
        }
        cursor.close()
    }
    return valueData
}

fun retrieveValueFromIndex(db: DBHelper, index: Int): UserModel? {
    var userModel: UserModel? = null
    db.readableDatabase.use {
        val query = it.rawQuery("SELECT * FROM Login where id = ?", arrayOf(index.toString()))
        while (query.moveToNext()) {
            userModel = UserModel(
                email = query.getString(1),
                password = query.getString(2),
            )
        }
        query.close()
    }
    return userModel
}

fun updateValueFromSpecificIndex(
    db: DBHelper,
    index: Int,
    username: String,
    password: String
): Int? {
    val data = db.writableDatabase.use {
        val data = ContentValues().apply {
            put(USERNAME, username)
            put(PASSWORD, password)
        }
        val whereClause = "id = ?"
        val whereValue = arrayOf(index.toString())
        it.update("Login", data, whereClause, whereValue)
    }
    println("Result $data")
    return data
}