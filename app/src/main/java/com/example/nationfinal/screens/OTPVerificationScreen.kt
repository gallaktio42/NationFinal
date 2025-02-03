package com.example.nationfinal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nationfinal.viewmodel.OTPVerViewModel
import com.example.nationfinal.R
import com.example.nationfinal.Routes
import com.example.nationfinal.ui.theme.NationFinalTheme

@Composable
fun OTPVerificationScreen(
    viewModel: OTPVerViewModel = viewModel(),
    navController: NavController,
    email: String
) {
    var isError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(viewModel.token) {
        if (viewModel.token.length == 6) {
            val isSuccess = viewModel.verifyOTP(context, email, viewModel.token, navController)
            isError = !isSuccess
        }
    }
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
                navController.navigate(Routes.SignIn.route) {
                    popUpTo(Routes.SignIn.route) {
                        inclusive = true
                    }
                }
            })
        )
        TitleText()
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text("OTP Код")
            }
            Spacer(Modifier.height(10.dp))
            BasicTextField(
                value = viewModel.token,
                onValueChange = {
                    if (it.length <= 6) {
                        viewModel.token = it
                    }
                },
                Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    repeat(6) { index ->
                        val number = when {
                            index >= viewModel.token.length -> ""
                            else -> viewModel.token[index]
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .width(46.dp)
                                    .height(99.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFEDEDEF))
                                    .border(1.dp, if (isError) Color.Red else Color.Transparent, RoundedCornerShape(12.dp))
                            ) {
                                Text(
                                    text = number.toString()
                                )
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(23.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp)
            ) {
                Text("Отправить заново", fontSize = 12.sp)
                Spacer(Modifier.weight(1f))
                ResendTimer(
                    viewModel.time,
                    viewModel.show,
                    onClick = {
                        viewModel.otpMessage(context, email)
                    })
            }
        }
    }
}

@Composable
private fun ResendTimer(
    time: Int,
    show: Boolean,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        if (!show && time >= 30) {
            Text(
                "00:30",
                fontSize = 12.sp,
            )
        } else if (!show && time < 30 && time >= 10) {
            Text(
                "0:$time",
                fontSize = 12.sp,
            )

        } else if (!show && time < 10) {
            Text(
                "0:0$time",
                fontSize = 12.sp,
            )
        } else {
            Text(
                "отправить",
                Modifier.clickable(onClick = onClick),
                fontSize = 12.sp,
            )
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
        Text("OTP Проверка", fontWeight = FontWeight.Bold, fontSize = 32.sp)
        Text("Пожалуйста, Проверьте Свою")
        Text("Электронную Почту, Чтобы Увидеть")
        Text("Код Подтверждения")
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    NationFinalTheme {
        OTPVerificationScreen(navController = rememberNavController(), email = "hohp")
    }
}
