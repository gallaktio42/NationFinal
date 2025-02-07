package com.example.nationfinal.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nationfinal.R
import com.example.nationfinal.Routes
import com.example.nationfinal.ui.theme.NationFinalTheme
import com.example.nationfinal.viewmodel.ProfileViewModel
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = viewModel()) {
    var barcode by remember { mutableStateOf(false) }
    var originalBrightness by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    LaunchedEffect(true) {
        try {
            originalBrightness =
                Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }
    if (viewModel.scan) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                    },
                    Modifier.padding(horizontal = 20.dp),
                    navigationIcon = {
                        Image(painterResource(R.drawable.icon_png_4x),
                            contentDescription = "hamburger",
                            Modifier
                                .size(44.dp)
                                .clickable {
                                    viewModel.scan = !viewModel.scan
                                })
                    },
                )
            },
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .clickable {
                        barcode = !barcode
                        verifyPermission(
                            context = context,
                            barcode,
                            originalBrightness
                        )
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Barcode(
                    Modifier,
                    width = 300.dp,
                    height = 150.dp,
                    resolutionFactor = 22,
                    type = BarcodeType.CODE_128,
                    value = viewModel.code
                )
            }
        }
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("Профиль", fontSize = 16.sp)
                    },
                    Modifier.padding(horizontal = 20.dp),
                    navigationIcon = {
                        Image(painterResource(R.drawable.icon_png_4x),
                            contentDescription = "hamburger",
                            Modifier
                                .size(44.dp)
                                .clickable { navController.navigate(Routes.Home.route) })
                    },
                )
            },
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 20.dp)
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(48.dp))
                    Image(
                        painterResource(R.drawable.steve_nig),
                        contentDescription = "nig",
                        Modifier.size(96.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "${viewModel.name} ${viewModel.surname}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Изменить фото профиля", fontSize = 12.sp, fontWeight = FontWeight.W600)
                }
                Column(
                    Modifier.fillMaxWidth(),
                ) {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.scan = !viewModel.scan }
                            .height(65.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFEDEDEF)
                        )
                    ) {
                        Row(
                            Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("Open", Modifier.rotate(-90F))
                            Spacer(Modifier.width(10.dp))
                            Box(
                                Modifier
                                    .clip(RoundedCornerShape(15.dp))
                                    .height(50.dp)
                                    .width(297.dp)
                                    .background(Color.White),
                            ) {
                                Barcode(
                                    Modifier
                                        .height(50.dp)
                                        .height(150.dp),
                                    type = BarcodeType.CODE_128,
                                    value = viewModel.code
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                    Text("Name")
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = viewModel.name,
                        onValueChange = {

                        },
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFEDEDEF),
                            unfocusedContainerColor = Color(0xFFEDEDEF),
                            unfocusedBorderColor = Color(0xFFEDEDEF),
                            focusedBorderColor = Color(0xFFEDEDEF),
                        )
                    )
                    Spacer(Modifier.height(17.dp))
                    Text("Surname")
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = viewModel.surname,
                        onValueChange = {

                        },
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFEDEDEF),
                            unfocusedContainerColor = Color(0xFFEDEDEF),
                            unfocusedBorderColor = Color(0xFFEDEDEF),
                            focusedBorderColor = Color(0xFFEDEDEF),
                        )
                    )
                    Spacer(Modifier.height(17.dp))
                    Text("Address")
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = viewModel.address,
                        onValueChange = {

                        },
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFEDEDEF),
                            unfocusedContainerColor = Color(0xFFEDEDEF),
                            unfocusedBorderColor = Color(0xFFEDEDEF),
                            focusedBorderColor = Color(0xFFEDEDEF),
                        )
                    )
                    Spacer(Modifier.height(17.dp))
                    Text("Number")
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = viewModel.number,
                        onValueChange = {

                        },
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFEDEDEF),
                            unfocusedContainerColor = Color(0xFFEDEDEF),
                            unfocusedBorderColor = Color(0xFFEDEDEF),
                            focusedBorderColor = Color(0xFFEDEDEF),
                        )
                    )
                }
            }
        }
    }
}

fun verifyPermission(context: Context, activate: Boolean, originalBright: Int) {
    if (Settings.System.canWrite(context)) {
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            100
        )
    } else {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        intent.data = Uri.parse("package: ${context.packageName}")
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


@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        ProfileScreen(navController = rememberNavController())
    }
}