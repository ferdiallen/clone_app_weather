package com.example.cloneopenweather.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cloneopenweather.R
import com.example.cloneopenweather.core.util.WeatherResource
import com.example.cloneopenweather.core.util.passwordLengthValidator

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    index: Int,
    viewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val updateState by viewModel.updateState.collectAsStateWithLifecycle()
    LaunchedEffect(updateState) {
        if(updateState is WeatherResource.Success){
            Toast.makeText(context,
                context.getString(R.string.update_successful_title), Toast.LENGTH_SHORT).show()
        }else if(updateState is WeatherResource.Error){
            Toast.makeText(context,
                context.getString(R.string.update_failed_title), Toast.LENGTH_SHORT).show()
        }
        viewModel.resetState()
    }
    val username by remember {
        derivedStateOf {
            viewModel.emailText
        }
    }
    val password by remember {
        derivedStateOf {
            viewModel.passwordText
        }
    }
    val isPasswordVisible by remember {
        derivedStateOf {
            viewModel.isPasswordVisible
        }
    }
    LaunchedEffect(Unit) {
        viewModel.initDbHelper(context = context)
        viewModel.retrieveLoginData(index)
    }
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = username, onValueChange = {
            viewModel.updateEmail(it)
        }, shape = RoundedCornerShape(12.dp), leadingIcon = {
            Icon(Icons.Filled.Email, contentDescription = "")
        }, maxLines = 1, placeholder = {
            Text(stringResource(R.string.email))
        })
        Spacer(modifier = Modifier.height(4.dp))
        Column(horizontalAlignment = Alignment.End) {
            TextField(
                value = password,
                onValueChange = {
                    viewModel.updatePassword(it)
                },
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(Icons.Filled.Lock, contentDescription = "")
                },
                maxLines = 1,
                placeholder = {
                    Text(stringResource(R.string.password))
                },
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.updatePasswordVisibility(!isPasswordVisible)
                    }) {
                        Icon(
                            if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = ""
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.updatePasswordLength(password.passwordLengthValidator())
            when {
                username.isEmpty() && password.isEmpty() -> Toast.makeText(
                    context,
                    context.getString(R.string.email_password_empty_warning),
                    Toast.LENGTH_SHORT
                ).show()
                username.isEmpty() -> Toast.makeText(
                    context,
                    context.getString(R.string.email_empty_warning),
                    Toast.LENGTH_SHORT
                ).show()
                password.passwordLengthValidator() != true -> Toast.makeText(
                    context,
                    context.getString(R.string.password_min_length),
                    Toast.LENGTH_SHORT
                ).show()
                password.isEmpty() -> Toast.makeText(
                    context,
                    context.getString(R.string.password_empty_warning),
                    Toast.LENGTH_SHORT
                ).show()
                else ->  viewModel.updateDatabaseValue(index)
            }
        }, modifier = Modifier.fillMaxWidth(0.6F)) {
            Text(
                stringResource(
                    R.string.update_btn
                ),
                color = Color.White
            )
        }
    }
}