package com.example.nationfinal.viewmodel

import android.annotation.SuppressLint
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nationfinal.model.SignUpTable
import com.example.nationfinal.utils.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.upload
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class ProfileViewModel : ViewModel() {
    var name by mutableStateOf("")
    var surname by mutableStateOf("")
    var address by mutableStateOf("")
    var number by mutableStateOf("")
    var code by mutableStateOf("")
    var imege by mutableStateOf("")
    var scan by mutableStateOf(false)
    var barcode by mutableStateOf(false)
    var switch by mutableStateOf(false)
    var upgrade by mutableStateOf(false)
    var circular by mutableStateOf(false)
    var originalBrightness by mutableIntStateOf(0)

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    init {
        try {
            initialize()
            originalBrightness =
                Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Exception) {
            Log.d("Error", "${e.message}")
        }
    }

    fun isValidName(): Boolean {
        return name.length >= 2
    }

    fun isValidSurname(): Boolean {
        return surname.length >= 3
    }

    fun isValidAddress(): Boolean {
        return address.length >= 4
    }

    fun isValidNumber(): Boolean {
        return number.length >= 9
    }

    fun initialize() {
        viewModelScope.launch {
            try {
                val uuid = supabase.auth.retrieveUserForCurrentSession().id
                code = uuid
                try {
                    val getName = supabase.from("SignUpTable").select() {
                        filter {
                            SignUpTable::id eq uuid
                        }
                    }.decodeSingle<SignUpTable>()
                    name = getName.name
                    surname = getName.surname.toString()
                    address = getName.address.toString()
                    number = getName.number.toString()
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }
                try {
                    val bucket = supabase.storage.from("profile")
                    val url = bucket.createSignedUrl(path = "${uuid}.png", expiresIn = 1000.minutes)
                    imege = url
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")
            }
        }
    }

    fun save() {
        viewModelScope.launch {
            try {
                supabase.from("SignUpTable").update({
                    SignUpTable::name setTo name
                    SignUpTable::surname setTo surname
                    SignUpTable::address setTo address
                    SignUpTable::number setTo number
                }) {
                    filter {
                        SignUpTable::id eq code
                    }
                }
                switch = false
            } catch (e: Exception) {
                Log.d("Error", "${e.message}")
            }
        }
    }

    fun uploadFile(byteArray: ByteArray) {
        viewModelScope.launch {
            try {
                val bucket = supabase.storage.from("profile")
                bucket.upload("${code}.png", byteArray) {
                    upsert = true
                }
                try {
                    initialize()
                } catch (e: Exception) {
                    Log.d("Error", "${e.message}")
                }
                upgrade = false
                circular = false
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