package com.example.nationfinal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nationfinal.R
import com.example.nationfinal.Routes
import com.example.nationfinal.viewmodel.SignInViewModel
import com.example.nationfinal.ui.theme.NationFinalTheme

@Composable
fun SignInScreen(viewModel: SignInViewModel = viewModel(), navController: NavController) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 55.dp),
        verticalArrangement = Arrangement.spacedBy(45.dp),

        ) {
        Image(
            painterResource(R.drawable.icon_png_4x),
            contentDescription = "back",
            Modifier.size(44.dp).clickable(onClick = {
                navController.navigate(Routes.Onboarding.route) {
                    popUpTo(Routes.Onboarding.route){
                        inclusive = true
                    }
                }
            })
        )
        TitleText()
        Column(Modifier.fillMaxWidth()) {
            Text("Email")
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                Modifier.fillMaxWidth(),
                placeholder = {
                    Text("xyz@gmail.com", color = Color.Black.copy(0.4f))
                },
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEDEDEF),
                    unfocusedContainerColor = Color(0xFFEDEDEF),
                    unfocusedBorderColor = Color(0xFFEDEDEF),
                    focusedBorderColor = Color(0xFFEDEDEF),
                )
            )
            Spacer(Modifier.height(29.dp))
            Text("Пароль")
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                Modifier.fillMaxWidth(),
                placeholder = {
                    Image(
                        painterResource(R.drawable.dots),
                        contentDescription = "dots",
                        Modifier
                            .width(69.dp)
                            .height(6.dp)
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { viewModel.visibility = !viewModel.visibility },
                    ) {
                        if (viewModel.visibility) {
                            Icon(
                                Icons.Outlined.Visibility,
                                contentDescription = "password",
                                tint = Color.Black.copy(0.3f)
                            )
                        } else {
                            Icon(
                                Icons.Outlined.VisibilityOff,
                                contentDescription = "password",
                                tint = Color.Black.copy(0.3f)
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEDEDEF),
                    unfocusedContainerColor = Color(0xFFEDEDEF),
                    unfocusedBorderColor = Color(0xFFEDEDEF),
                    focusedBorderColor = Color(0xFFEDEDEF),
                ),
                visualTransformation = if (viewModel.visibility) VisualTransformation.None else PasswordVisualTransformation(
                    mask = '\u25CF'
                )
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(
                    onClick = {navController.navigate(Routes.ForgotPass.route){
                        popUpTo(Routes.ForgotPass.route){
                            inclusive = true
                        }
                    } }
                ) {
                    Text("Восстановить", color = Color.Black.copy(0.3f))
                }
            }
            Spacer(Modifier.height(19.dp))
            Buttons(onClick = { viewModel.signIn(viewModel.email, viewModel.password, context, navController) })
        }
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Вы впервые?")
            Spacer(Modifier.width(3.dp))
            Text("Создать пользователя", Modifier.clickable(onClick = {
                navController.navigate(Routes.SignUp.route) {
                    popUpTo(Routes.SignUp.route){
                        inclusive = true
                    }
                }
            }))
        }
    }
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
                containerColor = Color(0xFF48B2E7)
            )
        ) {
            Text("Войти")
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
        Text("Привет!", fontWeight = FontWeight.Bold, fontSize = 32.sp)
        Text("Заполните Свои данные или")
        Text("продолжите через социальные медиа")
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        SignInScreen(navController = rememberNavController())
    }
}