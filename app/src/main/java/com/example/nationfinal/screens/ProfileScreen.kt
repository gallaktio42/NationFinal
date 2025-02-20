package com.example.nationfinal.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.nationfinal.R
import com.example.nationfinal.Routes
import com.example.nationfinal.ui.theme.NationFinalTheme
import com.example.nationfinal.utils.uriToByteArray
import com.example.nationfinal.viewmodel.ProfileViewModel
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = viewModel()) {
    val context = LocalContext.current
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    /* LaunchedEffect(true) {
         try {
             originalBrightness =
                 Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
         } catch (e: Exception) {
             Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
         }
     }*/
    viewModel.context = context
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
                                    //viewModel.barcode = !viewModel.barcode
                                    viewModel.verifyPermission(
                                        context = context,
                                        viewModel.scan,
                                        viewModel.originalBrightness
                                    )
                                }
                        )
                    },
                )
            },
        ) {
            Column(
                Modifier
                    .fillMaxSize(),
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
                        if (viewModel.switch) {
                            Button(
                                onClick = {
                                    viewModel.save()
                                },
                                Modifier
                                    .width(200.dp)
                                    .height(35.dp),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text(
                                    "Сохранить",
                                    fontSize = 14.sp,
                                    lineHeight = 22.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            Text("Профиль", fontSize = 16.sp)
                        }
                    },
                    Modifier.padding(horizontal = 20.dp),
                    navigationIcon = {
                        if (!viewModel.switch) {
                            Image(painterResource(R.drawable.icon_png_4x),
                                contentDescription = "hamburger",
                                Modifier
                                    .size(44.dp)
                                    .clickable { navController.navigate(Routes.Home.route) })
                        }
                    },
                    actions = {
                        if (!viewModel.switch) {
                            Image(painterResource(R.drawable.icon_png_4x),
                                contentDescription = "hamburger",
                                Modifier
                                    .size(44.dp)
                                    .clickable { viewModel.switch = !viewModel.switch })
                        }
                    }
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
                    if (viewModel.imege.isEmpty()) {
                        Icon(Icons.Default.PersonOutline, contentDescription = null, Modifier.size(96.dp))
                    } else {
                        AsyncImage(
                            model = viewModel.imege,
                            contentDescription = "nig",
                            Modifier.size(96.dp).clip(RoundedCornerShape(100.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "${viewModel.name} ${viewModel.surname}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600
                    )
                    Spacer(Modifier.height(8.dp))
                    if (viewModel.switch) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = if (viewModel.upgrade) "Загрузить выбранное фото" else "Изменить фото профиля",
                                Modifier.clickable {
                                    if (viewModel.upgrade) {
                                        if (imageUri != null) {
                                            val image = imageUri?.uriToByteArray(context)
                                            image?.let {
                                                viewModel.uploadFile(it)
                                            }
                                            viewModel.circular = !viewModel.circular
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Фото не было выбрано",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            viewModel.upgrade = !viewModel.upgrade
                                        }
                                    } else {
                                        launcher.launch("image/*")
                                        viewModel.upgrade = !viewModel.upgrade
                                    }
                                },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W600
                            )
                            if(imageUri != null && viewModel.circular){
                                CircularProgressIndicator(Modifier.size(22.dp))
                            }
                        }
                    }
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                ) {
                    if (!viewModel.switch) {
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.scan = !viewModel.scan
                                    viewModel.verifyPermission(
                                        context = context,
                                        viewModel.scan,
                                        viewModel.originalBrightness
                                    )
                                }
                                .height(65.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFEDEDEF)
                            )
                        ) {
                            Row(
                                Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
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
                                        showProgress = false,
                                        type = BarcodeType.CODE_128,
                                        value = viewModel.code
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                    Text("Name")
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = viewModel.name,
                        onValueChange = {
                            viewModel.name = it
                        },
                        Modifier.fillMaxWidth(),
                        enabled = if (viewModel.switch) true else false,
                        trailingIcon = {
                            if (viewModel.switch) {
                                if (viewModel.isValidName()) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.Blue
                                    )
                                }
                            }
                        },
                        isError = if (viewModel.name.isEmpty() || viewModel.isValidName()) false else true,
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
                            viewModel.surname = it
                        },
                        Modifier.fillMaxWidth(),
                        enabled = if (viewModel.switch) true else false,
                        trailingIcon = {
                            if (viewModel.switch) {
                                if (viewModel.isValidSurname()) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.Blue
                                    )
                                }
                            }
                        },
                        isError = if (viewModel.surname.isEmpty() || viewModel.isValidSurname()) false else true,
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
                            viewModel.address = it
                        },
                        Modifier.fillMaxWidth(),
                        enabled = if (viewModel.switch) true else false,
                        trailingIcon = {
                            if (viewModel.switch) {
                                if (viewModel.isValidAddress()) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.Blue
                                    )
                                }
                            }
                        },
                        isError = if (viewModel.address.isEmpty() || viewModel.isValidAddress()) false else true,
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
                            viewModel.number = it
                        },
                        Modifier.fillMaxWidth(),
                        enabled = if (viewModel.switch) true else false,
                        trailingIcon = {
                            if (viewModel.switch) {
                                if (viewModel.isValidNumber()) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.Blue
                                    )
                                }
                            }
                        },
                        isError = if (viewModel.number.isEmpty() || viewModel.isValidNumber()) false else true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFEDEDEF),
                            unfocusedContainerColor = Color(0xFFEDEDEF),
                            unfocusedBorderColor = Color(0xFFEDEDEF),
                            focusedBorderColor = Color(0xFFEDEDEF),
                        )
                    )
                    Spacer(Modifier.padding(top = 130.dp))
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        ProfileScreen(navController = rememberNavController())
    }
}