package com.example.nationfinal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nationfinal.model.Bucket
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class CardBucketViewModel : ViewModel() {
    var id by mutableStateOf<Int?>(null)
    var data by mutableStateOf<List<Bucket>>(listOf())

    init {
        viewModelScope.launch {
            data = getData()
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
        }
    }

    fun insert() {
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

    fun insertReverse() {
        viewModelScope.launch {
            try {
                if (data.find { it.idSneaker == id }!!.count > 0) {
                    supabase.from("Bucket")
                        .update(
                            {
                                Bucket::count setTo data.find { it.idSneaker == id }!!.count - 1
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
}
