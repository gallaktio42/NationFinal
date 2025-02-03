package com.example.nationfinal.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.nationfinal.model.Favorite
import com.example.nationfinal.model.PopularSneakers
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class FavoriteViewModel : ViewModel() {

    var data by mutableStateOf<List<PopularSneakers>>(listOf())

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
}
