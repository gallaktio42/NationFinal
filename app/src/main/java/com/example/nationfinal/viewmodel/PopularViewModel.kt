package com.example.nationfinal.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nationfinal.model.Bucket
import com.example.nationfinal.model.Favorite
import com.example.nationfinal.model.PopularSneakers
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class PopularViewModel : ViewModel() {

    var id by mutableStateOf<Int?>(null)
    var data by mutableStateOf<List<PopularSneakers>>(listOf())
    var dataBucket by mutableStateOf<List<Bucket>>(listOf())
    var dataFavorite by mutableStateOf<List<Favorite>>(listOf())

    init {
        try {
            inititalize()
        } catch (e: Exception) {
            Log.d("Error", "${e.message}")
        }
    }

    fun inititalize() {
        viewModelScope.launch {
            try {
                val uuid = supabase.auth.retrieveUserForCurrentSession().id
                try {
                    val popular = supabase.from("PopularSneakers")
                        .select() {
                            filter {
                                PopularSneakers::best eq true
                            }
                        }
                        .decodeList<PopularSneakers>()
                    data = popular
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }

                try {
                    val fav = supabase.from("Favorite")
                        .select {
                            filter {
                                Favorite::uuid eq uuid
                            }
                        }.decodeList<Favorite>()
                    dataFavorite = fav
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }

                try {
                    val buck = supabase.from("Bucket")
                        .select {
                            filter {
                                Bucket::uuid eq uuid
                            }
                        }.decodeList<Bucket>()
                    dataBucket = buck
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")
            }
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


    fun delete() {
        viewModelScope.launch {
            try {
                supabase.from("Favorite")
                    .delete() {
                        filter {
                            Favorite::idSneaker eq id
                        }
                    }
                try {
                    inititalize()
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")
            }
        }
    }

    fun insert2_0() {
        viewModelScope.launch {
            try {
                supabase.from("Favorite")
                    .insert(Favorite(idSneaker = id!!))
                try {
                    inititalize()
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
                        .update({
                            Bucket::count setTo dataBucket.find { it.idSneaker == id }!!.count + 1
                        }) {
                            filter {
                                Bucket::idSneaker eq id
                            }
                        }
                    try {
                        inititalize()
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

    fun insert() {
        viewModelScope.launch {
            try {
                supabase.from("Bucket")
                    .insert(Bucket(idSneaker = id!!, count = 1))
                try {
                    inititalize()
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")
            }
        }
    }
}
