package com.example.nationfinal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Drafts
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.nationfinal.viewmodel.ForgotPassViewModel
import com.example.nationfinal.R
import com.example.nationfinal.Routes
import com.example.nationfinal.ui.theme.NationFinalTheme

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPassViewModel = viewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 55.dp),
        verticalArrangement = Arrangement.spacedBy(45.dp),

        ) {
        if (viewModel.alert) {
            PopUp(onClick = {
                viewModel.alert = false
                navController.navigate("${Routes.OTPVerify.route}/${viewModel.email}") {
                    popUpTo(Routes.OTPVerify.route) {
                        inclusive = true
                    }
                }
            })
        }
        Image(
            painterResource(R.drawable.icon_png_4x),
            contentDescription = "back",
            Modifier.size(44.dp).clickable(onClick = {
                navController.navigate(Routes.SignIn.route) {
                    popUpTo(Routes.SignIn.route) {
                        inclusive = true
                    }
                }
            })
        )
        TitleText()
        Column(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                Modifier.fillMaxWidth(),
                placeholder = {
                    Text("xyz@gmail.com")
                },
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEDEDEF),
                    unfocusedContainerColor = Color(0xFFEDEDEF),
                    unfocusedBorderColor = Color(0xFFEDEDEF),
                    focusedBorderColor = Color(0xFFEDEDEF),
                )
            )
        }
        Buttons(onClick = { viewModel.forgotPassword(viewModel.email, context, navController) })
    }
}

@Composable
private fun PopUp(onClick: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onClick,
        confirmButton = {},
        title = {
            Text("Проверьте Ваш Email", fontSize = 16.sp, fontWeight = FontWeight.Black)
        },
        icon = {
            Box(
                Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(44.dp))
                    .background(Color(0XFF48B2e7)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Drafts, contentDescription = "email", tint = Color.White)
            }
        },
        text = {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Мы Отправили Код Восстановления Пароля На Вашу Электронную Почту.",
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}

@Composable
private fun Buttons(onClick: () -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Button(
            onClick = onClick,
            Modifier
                .fillMaxWidth()
                .width(335.dp)
                .height(50.dp),
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0XFF48B2e7)
            )
        ) {
            Text("Отправить")
        }
    }
}

@Composable
private fun TitleText() {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Забыл Пароль", fontWeight = FontWeight.Bold, fontSize = 32.sp)
        Text("Введите Свою Учетную Запись")
        Text("Для Сброса")
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    NationFinalTheme {
        ForgotPasswordScreen(navController = rememberNavController())
    }
}