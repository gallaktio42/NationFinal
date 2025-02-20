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
import com.example.nationfinal.Routes
import com.example.nationfinal.model.SignUpTable
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class SideMenuViewModel : ViewModel() {
    var nam by mutableStateOf("")
    var imege by mutableStateOf("")

    init {
        try {
            initialize()
        } catch (e: Exception) {
            Log.d("Error", "${e.message}")
        }
    }


    fun initialize() {
        viewModelScope.launch {
            try {
                val uuid = supabase.auth.retrieveUserForCurrentSession().id
                try {
                    val name = supabase.from("SignUpTable")
                        .select {
                            filter {
                                SignUpTable::id eq uuid
                            }
                        }.decodeSingle<SignUpTable>()
                    nam = name.name
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }

                try {
                    val bucket = supabase.storage.from("profile")
                    val url = bucket.createSignedUrl(path = "${uuid}.png", expiresIn = 1000.minutes)
                    imege = url
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")
            }
        }
    }

    fun signOut(context: Context, navController: NavController) {
        viewModelScope.launch {
            try {
                supabase.auth.signOut()
                navController.navigate(Routes.SignIn.route) {
                    popUpTo(Routes.SignIn.route) {
                        inclusive = true
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
