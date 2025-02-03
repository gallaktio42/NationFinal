package com.example.nationfinal.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.nationfinal.Routes

@Composable
fun OnBoardScreen(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("OnBoard")
        Button(onClick = {navController.navigate(Routes.SignIn.route){
            popUpTo(Routes.SignIn.route){
                inclusive = true
            }
        } }) { Text("Back") }
    }
}