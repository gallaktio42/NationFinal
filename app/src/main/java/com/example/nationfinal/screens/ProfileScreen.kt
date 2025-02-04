package com.example.nationfinal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = viewModel()) {
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
                .padding(horizontal = 20.dp)) {
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
                Text("${viewModel.name} ${viewModel.surname}", fontSize = 20.sp, fontWeight = FontWeight.W600)
                Spacer(Modifier.height(8.dp))
                Text("Изменить фото профиля", fontSize = 12.sp, fontWeight = FontWeight.W600)
            }
            Column(
                Modifier.fillMaxWidth(),
            ) {
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


@Preview(showBackground = true)
@Composable
private fun Preview(){
    NationFinalTheme {
        ProfileScreen(navController = rememberNavController())
    }
}