package com.example.cloneopenweather.presentation.login

import android.content.Context
import android.content.res.Resources
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.cloneopenweather.core.util.WeatherResource
import com.example.cloneopenweather.data.local.database.DBHelper
import com.example.cloneopenweather.data.local.database.insertLoginValue
import com.example.cloneopenweather.data.local.database.retrieveValueFromIndex
import com.example.cloneopenweather.data.local.database.retrieveValueLogin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private var db: DBHelper? = null
    var emailText by mutableStateOf("")
        private set
    var passwordText by mutableStateOf("")
        private set
    var isRegistration by mutableStateOf(false)
        private set
    var isPasswordVisible by mutableStateOf(false)
        private set
    var isLenghtOfPasswordLess by mutableStateOf(true)
        private set

    private val _loginState = MutableStateFlow<WeatherResource<Int>>(WeatherResource.Idle)
    val loginState = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<WeatherResource<String>>(WeatherResource.Idle)
    val registerState = _registerState.asStateFlow()


    fun updateEmail(data: String) {
        emailText = data
    }

    fun updatePassword(data: String) {
        passwordText = data
    }

    fun updateRegistration(data: Boolean) {
        isRegistration = data
    }

    fun updatePasswordVisibility(data: Boolean) {
        isPasswordVisible = data

    }

    fun initDbHelper(context: Context) {
        db = DBHelper(context)
    }

    fun updatePasswordLength(data: Boolean) {
        isLenghtOfPasswordLess = data
    }

    fun registerLogin() {
        if (db == null) {
            return
        }
        val data = db?.let {
            insertLoginValue(it, emailText, passwordText)
        }
        _registerState.update {
            if (data == -1L) {
                WeatherResource.Error("Error while registering")
            } else if (data == -2L) {
                WeatherResource.Error("Account already exist")
            } else {
                WeatherResource.Success("Register Successful")
            }

        }
    }

    fun loginWithAccount() {
        if (db == null) {
            return
        }
        val result = db?.let {
            try {
                retrieveValueLogin(it, emailText.replace(" ", ""), passwordText.replace(" ", ""))
            } catch (e: Exception) {
                _loginState.update {
                    WeatherResource.Error(e.message.toString())
                }
                null
            }
        }
        if (result != null) {
            _loginState.update {
                WeatherResource.Success(result.second)
            }
        } else {
            _loginState.update {
                WeatherResource.Error("Account not found")
            }
        }
    }

    fun resetState() {
        _loginState.update {
            WeatherResource.Idle
        }
        _registerState.update {
            WeatherResource.Idle
        }
    }
}