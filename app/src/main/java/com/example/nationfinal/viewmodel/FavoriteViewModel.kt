package com.example.nationfinal.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

class FavoriteViewModel : ViewModel() {

    var id by mutableStateOf<Int?>(null)
    var data by mutableStateOf<List<PopularSneakers>>(listOf())
    var dataBucket by mutableStateOf<List<Bucket>>(listOf())

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
                    val favorites = supabase.from("Favorite").select {
                        filter {
                            Favorite::uuid eq uuid
                        }
                    }.decodeList<Favorite>()
                    val favoriteSneakerIds = favorites.map { it.idSneaker }
                    // Используем фильтрацию по каждому элементу
                    val popularSneakers = mutableListOf<PopularSneakers>()
                    for (id in favoriteSneakerIds) {
                        val sneaker = supabase.from("PopularSneakers").select {
                            filter {
                                PopularSneakers::id eq id  // Фильтруем по ID
                            }
                        }.decodeList<PopularSneakers>()

                        popularSneakers.addAll(sneaker)
                    }
                    data = popularSneakers
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }

                try {
                    val buck = supabase.from("Bucket").select {
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

    suspend fun getFavoriteSneakers(): List<PopularSneakers> {
        try {
            val uuid = supabase.auth.retrieveUserForCurrentSession().identities
            val favorites = supabase.from("Favorite").select {
                filter {
                    Favorite::uuid eq uuid!!.first().userId
                }
            }.decodeList<Favorite>()
            Log.d("MyUUID", "$favorites")
            val favoriteSneakerIds = favorites.map { it.idSneaker }

            // Используем фильтрацию по каждому элементу
            val popularSneakers = mutableListOf<PopularSneakers>()

            for (id in favoriteSneakerIds) {
                val sneaker = supabase.from("PopularSneakers").select {
                    filter {
                        PopularSneakers::id eq id  // Фильтруем по ID
                    }
                }.decodeList<PopularSneakers>()

                popularSneakers.addAll(sneaker)
            }

            return popularSneakers

        } catch (e: Exception) {
            throw e
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
                } catch (e: Exception) {
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
