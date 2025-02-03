package com.example.nationfinal.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.nationfinal.Routes
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OTPVerViewModel : ViewModel() {
    var token by mutableStateOf("")
    var time by mutableIntStateOf(30)
    var show by mutableStateOf(false)

    init {
        startCountdown()
    }

    private fun startCountdown() {
        viewModelScope.launch {
            while (time > 0) {
                delay(1000L)
                time--
            }
            show = true
        }
    }

    fun resetCountdown() {
        time = 30
        show = false
        startCountdown()
    }

    suspend fun verifyOTP(
        context: Context,
        email: String,
        token: String,
        navController: NavController
    ): Boolean {
        try {
            if (this.token == token && this.token.length == 6) {
                supabase.auth.verifyEmailOtp(OtpType.Email.EMAIL, email, token)
                Toast.makeText(context, "Verify Success", Toast.LENGTH_LONG).show()
                supabase.auth.updateUser {
                    password = token
                }
                navController.navigate(Routes.SignIn.route) {
                    popUpTo(Routes.SignIn.route) {
                        inclusive = true
                    }
                }
                return true
            }else{
                return false
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            return false
        }
    }

    fun otpMessage(context: Context, email: String) {
        viewModelScope.launch {
            try {
                supabase.auth.resetPasswordForEmail(email)
                Toast.makeText(context, "Send!", Toast.LENGTH_SHORT).show()
                time = 30
                show = false
                startCountdown()
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                throw e
            }
        }
    }
}
