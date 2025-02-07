package com.example.nationfinal.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nationfinal.model.SignUpTable
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    var name by mutableStateOf("")
    var surname by mutableStateOf("")
    var address by mutableStateOf("")
    var number by mutableStateOf("")
    var code by mutableStateOf("")
    var scan by mutableStateOf(false)
    var barcode by mutableStateOf(false)
    var originalBrightness by mutableIntStateOf(0)
    lateinit var context: Context

    init {
        try {
            initialize()
            originalBrightness = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Exception) {
            Log.d("Error", "${e.message}")
        }
    }

    fun initialize() {
        viewModelScope.launch {
            try {
                val uuid = supabase.auth.retrieveUserForCurrentSession().id
                code = uuid
                val getName = supabase.from("SignUpTable").select() {
                    filter {
                        SignUpTable::id eq uuid
                    }
                }.decodeSingle<SignUpTable>()
                name = getName.name
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")
            }
        }
    }

    fun verifyPermission(context: Context, activate: Boolean, originalBright: Int) {
        if (Settings.System.canWrite(context)) {
            toggleBrightness(context, activate, originalBright)
        } else {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = Uri.parse("package:${context.packageName}")
            context.startActivity(intent)
        }
    }


    fun toggleBrightness(context: Context, activate: Boolean, originalBright: Int) {
        if (activate) {
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            )
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                204
            )
        } else {
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
            )
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                originalBright
            )
        }
    }
}