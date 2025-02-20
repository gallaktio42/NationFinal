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
import com.example.nationfinal.model.Bucket
import com.example.nationfinal.model.Favorite
import com.example.nationfinal.model.PopularSneakers
import com.example.nationfinal.model.SignUpTable
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var id by mutableStateOf<Int?>(null)
    val category = listOf("All", "Outdoor", "Tennis", "Run", "Tech", "Keckers")
    var data by mutableStateOf<List<PopularSneakers>>(listOf())

    //var dataAll by mutableStateOf<List<PopularSneakers>>(listOf())
    var dataFavorite by mutableStateOf<List<Favorite>>(listOf())
    var dataBucket by mutableStateOf<List<Bucket>>(listOf())
    var nam by mutableStateOf("")

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
                    val image = supabase.from("PopularSneakers")
                        .select() {
                            filter {
                                PopularSneakers::best eq true
                            }
                        }
                        .decodeList<PopularSneakers>()
                    data = image
                    //data = getDataPopular()
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }

                try {
                    val fav = supabase.from("Favorite")
                        .select() {
                            filter {
                                Favorite::uuid eq uuid
                            }
                        }
                        .decodeList<Favorite>()
                    dataFavorite = fav
                    //dataFavorite = getFavorite(uuid)
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }

                try {
                    val buck = supabase.from("Bucket")
                        .select() {
                            filter {
                                Bucket::uuid eq uuid
                            }
                        }
                        .decodeList<Bucket>()
                    dataBucket = buck
                    //dataBucket = getBucket(uuid)
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }

                try {
                    val name = supabase.from("SignUpTable")
                        .select {
                            filter {
                                SignUpTable::id eq uuid
                            }
                        }.decodeSingle<SignUpTable>()
                    nam = name.name
                }catch (e: Exception){
                    Log.d("Error", "${e.message}")
                }

            } catch (e: Exception) {
                Log.d("Error", "${e.message}")
            }
        }
    }

    suspend fun getBucket(uuid: String): List<Bucket> {
        try {
            //val uuid = supabase.auth.retrieveUserForCurrentSession().identities
            val image = supabase.from("Bucket")
                .select() {
                    filter {
                        Bucket::uuid eq uuid
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
            return image
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getFavorite(uuid: String): List<Favorite> {
        try {
            //val uuid = supabase.auth.retrieveUserForCurrentSession().identities
            val image = supabase.from("Favorite")
                .select() {
                    filter {
                        Favorite::uuid eq uuid
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
            return image
        } catch (e: Exception) {
            throw e
        }
    }

    fun insert2_0() {
        viewModelScope.launch {
            try {
                supabase.from("Favorite")
                    .insert(Favorite(idSneaker = id!!))
                try {
                    initialize()
                }catch (e: Exception){
                    Log.d("Error", "${e.message}")
                }
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            try {
                supabase.from("Favorite")
                    .delete {
                        filter {
                            Favorite::idSneaker eq id
                        }
                    }
                try {
                    initialize()
                }catch (e: Exception){
                    Log.d("Error", "${e.message}")
                }
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")
            }
        }
    }


    fun insert() {
        viewModelScope.launch {
            try {
                supabase.from("Bucket")
                    .insert(Bucket(idSneaker = id!!, count = 1))
                try {
                    initialize()
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            try {
                if (dataBucket.find { it.idSneaker == id }!!.count > 0) {
                    supabase.from("Bucket")
                        .update(
                            {
                                Bucket::count setTo dataBucket.find { it.idSneaker == id }!!.count + 1
                            }
                        ) {
                            filter {
                                Bucket::idSneaker eq id
                            }
                        }
                    try {
                        initialize()
                    } catch (e: Exception) {
                        Log.d("Error", "${e.message}")
                    }
                } else {
                    delete()
                }
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")
            }
        }
    }
}