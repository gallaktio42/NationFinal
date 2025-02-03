package com.example.nationfinal.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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

class BucketViewModel() : ViewModel() {
    var data by mutableStateOf<List<PopularSneakers>>(listOf())
    var count by mutableIntStateOf(0)
    var price by mutableFloatStateOf(0.0F)
    var all by mutableFloatStateOf(0.0F)
    var dataFavorite by mutableStateOf<List<Bucket>>(listOf())

    //experimental
    var id by mutableStateOf<Int?>(null)

    init {
        viewModelScope.launch {
            try {
                count = getCount()
                price = getPrice()
                data = getBucket()
                dataFavorite = getCountSneaker()
                all = price + 60.20F
            } catch (e: Exception) {

            }
        }
    }

    suspend fun getBucket(): List<PopularSneakers> {
        try {
            val uuid = supabase.auth.retrieveUserForCurrentSession().identities
            val bucket = supabase.from("Bucket")
                .select() {
                    filter {
                        Bucket::uuid eq uuid?.first()?.userId
                    }
                }.decodeList<Bucket>()
            val bucketIds = bucket.map { it.idSneaker }
            val bucketSneaker = mutableListOf<PopularSneakers>()
            for (id in bucketIds) {
                val crocs = supabase.from("PopularSneakers")
                    .select {
                        filter {
                            PopularSneakers::id eq id
                        }
                    }.decodeList<PopularSneakers>()
                bucketSneaker.addAll(crocs)
            }
            Log.d("Bucket", "$bucketSneaker")
            return bucketSneaker
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getCount(): Int {
        try {
            val uuid = supabase.auth.retrieveUserForCurrentSession().identities
            val bucket = supabase.from("Bucket")
                .select {
                    filter {
                        Bucket::uuid eq uuid?.first()?.userId
                    }
                }.decodeList<Bucket>()
            return bucket.count()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getPrice(): Float {
        try {
            val bucketSneaker = getBucket()
            val uuid = supabase.auth.retrieveUserForCurrentSession().identities
            val bucket = supabase.from("Bucket")
                .select() {
                    filter {
                        Bucket::uuid eq uuid?.first()?.userId
                    }
                }.decodeList<Bucket>()
            var allSneakers = 0.0F
            for (item in bucket) {
                allSneakers += (item.count.toFloat()) * bucketSneaker.find { it.id == item.idSneaker }!!.price
            }
            return allSneakers
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getCountSneaker(): List<Bucket> {
        try {
            val uuid = supabase.auth.retrieveUserForCurrentSession().identities
            val image = supabase.from("Bucket")
                .select() {
                    filter {
                        Favorite::uuid eq uuid?.first()?.userId
                    }
                }
                .decodeList<Bucket>()
            return image
        } catch (e: Exception) {
            throw e
        }
    }

    //experimental

    fun insert() {
        viewModelScope.launch {
            try {
                if (dataFavorite.find { it.idSneaker == id }!!.count > 0) {
                    supabase.from("Bucket")
                        .update(
                            {
                                Bucket::count setTo dataFavorite.find { it.idSneaker == id }!!.count + 1
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
            price = getPrice()
            data = getBucket()
            dataFavorite = getCountSneaker()
            all = getPrice() + 60.20F
        }
    }

    fun insertReverse() {
        viewModelScope.launch {
            try {
                if (dataFavorite.find { it.idSneaker == id }!!.count > 0) {
                    supabase.from("Bucket")
                        .update(
                            {
                                Bucket::count setTo dataFavorite.find { it.idSneaker == id }!!.count - 1
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
            price = getPrice()
            data = getBucket()
            dataFavorite = getCountSneaker()
            all = getPrice() + 60.20F
        }
    }


    fun delete() {
        viewModelScope.launch {
            try {
                supabase.from("Bucket")
                    .delete {
                        filter {
                            Bucket::idSneaker eq id
                        }
                    }
            } catch (e: Exception) {
                throw e
            }
            price = getPrice()
            data = getBucket()
            dataFavorite = getCountSneaker()
            all = getPrice() + 60.20F
        }
    }
}
