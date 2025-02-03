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

    fun signIn(userEmail: String, userPass: String, context: Context, navController: NavController) {
        viewModelScope.launch {
            try {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    supabase.auth.signInWith(Email) {
                        email = userEmail
                        password = userPass
                    }
                    navController.navigate(Routes.Home.route){
                        popUpTo(Routes.Home.route){
                            inclusive = true
                        }
                    }
                } else {
                    Toast.makeText(context, "Заполни поля", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
