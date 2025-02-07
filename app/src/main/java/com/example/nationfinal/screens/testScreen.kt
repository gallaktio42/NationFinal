package com.example.nationfinal.screens


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nationfinal.R
import com.example.nationfinal.ui.theme.NationFinalTheme
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType

@Composable
fun BottomBarScreens() {
    var isBarcodeOpen by remember { mutableStateOf(false) }
    var originalBrightness by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    LaunchedEffect(originalBrightness) {
        originalBrightness =
            Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        Log.d("MyLog", "$originalBrightness - default")

    }
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Barcode(
            Modifier.clickable {
                isBarcodeOpen = !isBarcodeOpen
                toggleBarcodeState(context, isBarcodeOpen, originalBrightness)
            },
            type = BarcodeType.CODE_128,
            value = "123123dasad13das"
        )
    }
}

private fun toggleBarcodeState(context: Context, open: Boolean, originalBrightness: Int) {
    if (Settings.System.canWrite(context)) {
        val brightnessValue = if (open) {
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            )
            Log.d("MyLog", "80%")
            204
        } else {
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
            )
            Log.d("MyLog", "default brightness - $originalBrightness")
            originalBrightness

        }
        try {
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                brightnessValue
            )
        } catch (e: Exception) {
            // Обработка исключений
            e.printStackTrace()
        }
    } else {
        // Запрос разрешения у пользователя
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        intent.data = Uri.parse("package:${context.packageName}")
        context.startActivity(intent)
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        BottomBarScreens()
    }
}