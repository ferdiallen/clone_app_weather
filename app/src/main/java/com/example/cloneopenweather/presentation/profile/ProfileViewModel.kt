package com.example.cloneopenweather.presentation.profile

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cloneopenweather.core.util.WeatherResource
import com.example.cloneopenweather.data.local.database.DBHelper
import com.example.cloneopenweather.data.local.database.retrieveValueFromIndex
import com.example.cloneopenweather.data.local.database.updateValueFromSpecificIndex
import com.example.cloneopenweather.domain.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private var db: DBHelper? = null
    var emailText by mutableStateOf("")
        private set
    var passwordText by mutableStateOf("")
        private set
    var isPasswordVisible by mutableStateOf(false)
        private set
    var isLenghtOfPasswordLess by mutableStateOf(true)
        private set

    private val _updateState =
        MutableStateFlow<WeatherResource<Int>>(WeatherResource.Idle)
    val updateState = _updateState.asStateFlow()

    fun updateEmail(data: String) {
        emailText = data
    }

    fun updatePassword(data: String) {
        passwordText = data
    }

    fun updatePasswordVisibility(data: Boolean) {
        isPasswordVisible = data

    }

    fun updatePasswordLength(data: Boolean) {
        isLenghtOfPasswordLess = data
    }


    fun retrieveLoginData(index: Int) {
        if (db == null) {
            return
        }
        val data = db?.let {
            retrieveValueFromIndex(it, index)
        }
        if (data == null) {
            _updateState.update {
                WeatherResource.Error("Data Not Found")
            }
        } else {
            emailText = data.email
            passwordText = data.password
        }
    }

    fun updateDatabaseValue(index: Int) {
        if (db == null) {
            return
        }
        val data = db?.let {
            updateValueFromSpecificIndex(it, index, emailText, passwordText)
        }
        data?.let { value ->
            _updateState.update {
                if (data > 0) {
                    WeatherResource.Success(value)
                } else {
                    WeatherResource.Error("Update Failed")
                }
            }
            return
        }
        _updateState.update {
            WeatherResource.Error("Update Failed")
        }
    }

    fun resetState() {
        _updateState.update {
            WeatherResource.Idle
        }
    }

    fun initDbHelper(context: Context) {
        db = DBHelper(context)
    }
}