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
    var text by mutableStateOf("")
    var active by mutableStateOf(false)
    var data by mutableStateOf<List<PopularSneakers>>(listOf())
    var filteredData by mutableStateOf(data)
    var history by mutableStateOf<List<SearchHistory>>(listOf())
    var dataFavorite by mutableStateOf<List<Favorite>>(listOf())
    var dataBucket by mutableStateOf<List<Bucket>>(listOf())

    init {
        viewModelScope.launch {
            try {
                data = getData()
                history = getHistory()
                dataFavorite = getFavorite()
                dataBucket = getBucket()
            }catch (e: Exception){

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
            } catch (e: Exception) {
                throw e
            }
            history = getHistory()
        }
    }

    fun search(text: String): List<PopularSneakers> {
        return data.filter {
            it.name.lowercase().startsWith(text.lowercase())
            //it.category.lowercase().startsWith(text.lowercase())
        }
    }
}
