package com.example.nationfinal.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nationfinal.model.SignUpTable
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    var name by mutableStateOf("")
    var surname by mutableStateOf("")
    var address by mutableStateOf("")
    var number by mutableStateOf("")

    init {
        try {
            initialize()
        }catch (e: Exception){
            Log.d("Error", "${e.message}")
        }
    }
    fun initialize(){
        viewModelScope.launch {
            try {
                val uuid = supabase.auth.retrieveUserForCurrentSession().id
                val getName = supabase.from("SignUpTable").select(){
                    filter {
                        SignUpTable::id eq uuid
                    }
                }.decodeSingle<SignUpTable>()
                name = getName.name
            }catch (e: Exception){
                Log.d("Error", "${e.message}")
            }
        }
    }
}
