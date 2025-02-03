package com.example.nationfinal.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.nationfinal.model.PopularSneakers
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.postgrest.from

class PopularViewModel: ViewModel() {
    var data by mutableStateOf<List<PopularSneakers>>(listOf())
    suspend fun getDataPopular(): List<PopularSneakers>{
        try {
            val image = supabase.from("PopularSneakers")
                .select(){
                    filter {
                        PopularSneakers::best eq true
                    }
                }
                .decodeList<PopularSneakers>()
            Log.d("MyTag", "$image")
            return image
        }catch (e: Exception){
            throw e
        }
    }
}
