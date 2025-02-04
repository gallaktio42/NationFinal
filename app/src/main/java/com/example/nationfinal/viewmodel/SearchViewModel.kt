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
import com.example.nationfinal.model.SearchHistory
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    var id by mutableStateOf<Int?>(null)
    var text by mutableStateOf("")
    var active by mutableStateOf(false)
    var data by mutableStateOf<List<PopularSneakers>>(listOf())
    var filteredData by mutableStateOf(data)
    var history by mutableStateOf<List<SearchHistory>>(listOf())
    var dataFavorite by mutableStateOf<List<Favorite>>(listOf())
    var dataBucket by mutableStateOf<List<Bucket>>(listOf())

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
                    val getData = supabase.from("PopularSneakers")
                        .select()
                        .decodeList<PopularSneakers>()
                    data = getData
                    //data = getData()
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }

                try {
                    val getHistory = supabase.from("SearchHistory")
                        .select() {
                            filter {
                                SearchHistory::uuid eq uuid
                            }
                        }
                        .decodeList<SearchHistory>()
                    history = getHistory
                    //history = getHistory()
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
                    //dataFavorite = getFavorite()
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
                    //dataBucket = getBucket()
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")
            }
        }
    }

    suspend fun getData(): List<PopularSneakers> {
        try {
            val data = supabase.from("PopularSneakers")
                .select()
                .decodeList<PopularSneakers>()
            return data
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getHistory(): List<SearchHistory> {
        try {
            val uuid = supabase.auth.retrieveUserForCurrentSession().id
            val data = supabase.from("SearchHistory")
                .select() {
                    filter {
                        SearchHistory::uuid eq uuid
                    }
                }
                .decodeList<SearchHistory>()
            return data
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getFavorite(): List<Favorite> {
        try {
            val uuid = supabase.auth.retrieveUserForCurrentSession().id
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

    suspend fun getBucket(): List<Bucket> {
        try {
            val uuid = supabase.auth.retrieveUserForCurrentSession().id
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

    fun insert(text: String) {
        viewModelScope.launch {
            try {
                supabase.from("SearchHistory")
                    .insert(SearchHistory(query = text))
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

    fun search(text: String): List<PopularSneakers> {
        return data.filter {
            it.name.lowercase().startsWith(text.lowercase())
            //it.category.lowercase().startsWith(text.lowercase())
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

    fun insertFav0rite() {
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

    fun insertBucket() {
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
