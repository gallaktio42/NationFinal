package com.example.nationfinal.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.nationfinal.model.QueneModel
import com.example.nationfinal.utils.QueneContains

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
class OnBoardViewModel : ViewModel() {
    private var originalQuene = QueneContains.queue.toMutableList()
    var quene = originalQuene
    var currentItem by mutableStateOf<QueneModel?>(null)
    //var isButton: Boolean = false
    //var isNavigate: Boolean = false
    var isFirstView: Boolean = false


    init {
        next()
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun next() {
        if (QueneContains.queue.count() > quene.count()) {
            isFirstView = false
        } else {
            isFirstView = true
        }
        val first = quene.removeFirst()
        currentItem = first
        println("$quene")
    }
}