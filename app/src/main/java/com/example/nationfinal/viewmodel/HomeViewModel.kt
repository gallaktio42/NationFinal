package com.example.nationfinal.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.nationfinal.Routes
import com.example.nationfinal.model.Bucket
import com.example.nationfinal.model.Favorite
import com.example.nationfinal.model.PopularSneakers
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val category = listOf("All", "Outdoor", "Tennis", "Run", "Tech", "Keckers")
    var data by mutableStateOf<List<PopularSneakers>>(listOf())
    var dataAll by mutableStateOf<List<PopularSneakers>>(listOf())
    var dataFavorite by mutableStateOf<List<Favorite>>(listOf())
    var dataBucket by mutableStateOf<List<Bucket>>(listOf())

    init {
        viewModelScope.launch {
            try {
                data = getDataPopular()
                dataAll = getData()
                dataFavorite = getFavorite()
                dataBucket = getBucket()
            } catch (e: Exception) {

            }
        }
    }

    suspend fun getBucket(): List<Bucket> {
        try {
            val uuid = supabase.auth.retrieveUserForCurrentSession().identities
            val image = supabase.from("Bucket")
                .select() {
                    filter {
                        Bucket::uuid eq uuid!!.first().userId
                    }
                }
                .decodeList<Bucket>()
            return image
        } catch (e: Exception) {
            throw e
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

    suspend fun getData(): List<PopularSneakers> {
        try {
            val image = supabase.from("PopularSneakers")
                .select()
                .decodeList<PopularSneakers>()
            Log.d("MyTag1", "$image")
            return image
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getFavorite(): List<Favorite> {
        try {
            val uuid = supabase.auth.retrieveUserForCurrentSession().identities
            val image = supabase.from("Favorite")
                .select() {
                    filter {
                        Favorite::uuid eq uuid!!.first().userId
                    }
                }
                .decodeList<Favorite>()
            return image
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getDataPopular(): List<PopularSneakers> {
        try {
            val image = supabase.from("PopularSneakers")
                .select() {
                    filter {
                        PopularSneakers::best eq true
                    }
                }
                .decodeList<PopularSneakers>()
            Log.d("MyTag", "$image")
            return image
        } catch (e: Exception) {
            throw e
        }
    }
}
