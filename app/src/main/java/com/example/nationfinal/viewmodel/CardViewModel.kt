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

class CardViewModel : ViewModel() {

    var id by mutableStateOf<Int?>(null)
    var isFavorite by mutableStateOf<List<PopularSneakers>>(listOf())
    var UUID by mutableStateOf<List<Favorite>>(listOf())
    var data by mutableStateOf<List<Bucket>>(listOf())

    init {
        viewModelScope.launch {
            try {
                UUID = getUUID()
                isFavorite = getFavorite()
                data = getData()
            } catch (e: Exception) {

            }
        }
    }

    fun insert2_0() {
        viewModelScope.launch {
            try {
                supabase.from("Favorite")
                    .insert(Favorite(idSneaker = id!!))
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun insert() {
        viewModelScope.launch {
            try {
                supabase.from("Bucket")
                    .insert(Bucket(idSneaker = id!!, count = 1))
            } catch (e: Exception) {
                throw e
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
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            try {
                if (data.find { it.idSneaker == id }!!.count > 0) {
                    supabase.from("Bucket")
                        .update(
                            {
                                Bucket::count setTo data.find { it.idSneaker == id }!!.count + 1
                            }
                        ) {
                            filter {
                                Bucket::idSneaker eq id
                            }
                        }
                } else {
                    delete()
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }

    /*fun insert() {
        viewModelScope.launch {
            try {
                val isFavoriteValue = isFavorite.find { it.id == id }
                Log.d("MyId", isFavoriteValue.toString())
                if (isFavoriteValue != null) {
                    val selectedItem = isFavoriteValue.isFav
                    if (!selectedItem) {
                        supabase.from("Favorite")
                            .insert(Favorite(idSneaker = id!!))
                        supabase.from("PopularSneakers")
                            .update({
                                set("isFav", true)
                            }) {
                                filter {
                                    PopularSneakers::id eq id
                                }
                            }
                        //Log.d("MyId", id.toString())
                    } else {
                        val uuidItem = UUID.find { it.idSneaker == id }
                        supabase.from("Favorite")
                            .delete {
                                filter {
                                    Favorite::idSneaker eq id
                                }
                            }
                        supabase.from("PopularSneakers")
                            .update({
                                set("isFav", false)
                            }) {
                                filter {
                                    PopularSneakers::id eq id
                                }
                            }
                    }
                } else {

                }
            } catch (e: Exception) {
                throw e
            }
        }
    }*/

    suspend fun getData(): List<Bucket> {
        try {
            val uuid = supabase.auth.retrieveUserForCurrentSession().identities
            val bucket = supabase.from("Bucket")
                .select() {
                    filter {
                        Bucket::uuid eq uuid!!.first().userId
                    }
                }.decodeList<Bucket>()
            return bucket
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getFavorite(): List<PopularSneakers> {
        try {
            val favorite = supabase.from("PopularSneakers").select().decodeList<PopularSneakers>()
            return favorite
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getUUID(): List<Favorite> {
        try {
            val favorite = supabase.from("Favorite").select().decodeList<Favorite>()
            return favorite
        } catch (e: Exception) {
            throw e
        }
    }

}
