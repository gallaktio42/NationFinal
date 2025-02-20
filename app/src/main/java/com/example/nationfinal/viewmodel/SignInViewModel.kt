package com.example.nationfinal.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.nationfinal.Routes
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    var visibility by mutableStateOf(false)
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var error by mutableStateOf("")
    var alert by mutableStateOf(false)

    fun isValidPassword(password: String): Boolean {
        val valid = password.length >= 6
        return valid
    }

    fun isValidEmail(email: String): Boolean{
        val regex = Regex("^[a-z0-9]{4,}+@[a-z0-9]{2,}+\\.[a-z]{2,}$")
        return regex.matches(email)
    }

    fun typeOfError(): String {
        if (password.isEmpty() && email.isEmpty()) {
            error = "Пустые поля"
            return error
        } else if (password.isEmpty()) {
            error = "Заполните пароль"
            return error
        } else if (email.isEmpty()) {
            error = "Заполните почту"
            return error
        } else if (!isValidPassword(password)) {
            error = "Введите корректный пароль"
            return error
        } else if (!isValidEmail(email)) {
            error = "Введите корректную почту"
            return error
        } else {
            error = "Некорректные данные"
            return error
        }
    }

    fun signIn(
        userEmail: String,
        userPass: String,
        context: Context,
        navController: NavController
    ) {
        viewModelScope.launch {
            try {
                if (email.isNotEmpty() && password.isNotEmpty() && isValidPassword(userPass) && isValidEmail(userEmail)) {
                    supabase.auth.signInWith(Email) {
                        email = userEmail
                        password = userPass
                    }
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Home.route) {
                            inclusive = true
                        }
                    }
                } else {
                    alert = true
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                alert = true
            }
        }
    }
}
