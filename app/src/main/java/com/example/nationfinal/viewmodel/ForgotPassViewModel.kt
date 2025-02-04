package com.example.nationfinal.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.nationfinal.model.SignUpTable
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class ForgotPassViewModel : ViewModel() {
    var email by mutableStateOf("")
    var alert by mutableStateOf(false)

    init{
        viewModelScope.launch {
            getUsers()
        }
    }

    fun forgotPassword(userEmail: String, context: Context, navController: NavController) {
        viewModelScope.launch {
            try {
                if (email.isNotEmpty() && getUsers()) {
                    supabase.auth.resetPasswordForEmail(userEmail)
                    alert = true
                } else {
                    Toast.makeText(context, "Заполните поле", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    suspend fun getUsers(): Boolean {
        try {
            val users = supabase.from("SignUpTable").select().decodeList<SignUpTable>()
            val user = users.find { email == it.email }
            if (user != null) {
                return true
            }else{
                return false
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
