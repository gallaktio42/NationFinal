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
import com.example.nationfinal.model.SignUpTable
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class SignUpViewModel: ViewModel() {
    var visibility by mutableStateOf(false)
    var check by mutableStateOf(false)
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun signUp(context: Context, userName: String, userEmail: String, userPass: String, navController: NavController){
        viewModelScope.launch {
            try {
                if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && check) {
                    supabase.auth.signUpWith(Email) {
                        email = userEmail
                        password = userPass
                    }
                    val user = SignUpTable(name = userName, email = userEmail)
                    supabase.from("SignUpTable").insert(user)
                    supabase.auth.signOut()
                    navController.navigate(Routes.SignIn.route){
                        popUpTo(Routes.SignIn.route){
                            inclusive = true
                        }
                    }
                }
                else{
                    if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                        Toast.makeText(context, "Заполни поля", Toast.LENGTH_LONG).show()
                    }
                    else if (!check){
                        Toast.makeText(context, "Прочитайте согласие на обработку данных", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(context, "Unknown", Toast.LENGTH_LONG).show()
                    }
                }
            }catch (e: Exception){
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
