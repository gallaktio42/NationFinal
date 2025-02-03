package com.example.nationfinal.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nationfinal.R
import com.example.nationfinal.Routes
import com.example.nationfinal.viewmodel.SignUpViewModel
import com.example.nationfinal.ui.theme.NationFinalTheme
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.VerticalPdfReaderState
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(viewModel: SignUpViewModel = viewModel(), navController: NavController) {
    val context = LocalContext.current
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Asset(R.raw.mazda),
        isZoomEnable = true
    )
    var switch by rememberSaveable { mutableStateOf(false) }

    if (switch) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("PDF Viewer")
                    },
                    navigationIcon = {
                        IconButton(onClick = { switch = false }) {
                            Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "back")
                        }
                    }
                )
            }
        ) {
            Column(Modifier.padding(it)) {
                PdfOpener(pdfState)
            }
        }
    } else {
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
            Column(Modifier.fillMaxWidth()) {
                Text("Ваше имя")
                OutlinedTextField(
                    value = viewModel.name,
                    onValueChange = { viewModel.name = it },
                    Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("xxxxxxxx", color = Color.Black.copy(0.4f))
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
                        IconButton(onClick = { viewModel.visibility = !viewModel.visibility }) {
                            if (viewModel.visibility) {
                                Icon(
                                    Icons.Outlined.Visibility,
                                    contentDescription = "on",
                                    tint = Color.Black.copy(0.3f)
                                )
                            } else {
                                Icon(
                                    Icons.Outlined.VisibilityOff,
                                    contentDescription = "off",
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
                Spacer(Modifier.height(19.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.size(25.dp), contentAlignment = Alignment.Center) {
                        Checkbox(
                            checked = viewModel.check,
                            onCheckedChange = { viewModel.check = it },
                            colors = CheckboxColors(
                                checkedCheckmarkColor = Color.Black,
                                uncheckedCheckmarkColor = Color.Black,
                                checkedBoxColor = Color(0xFFEDEDEF),
                                uncheckedBoxColor = Color(0xFFEDEDEF),
                                disabledCheckedBoxColor = Color.LightGray,
                                disabledUncheckedBoxColor = Color.LightGray,
                                disabledIndeterminateBoxColor = Color.LightGray,
                                checkedBorderColor = Color(0xFFEDEDEF),
                                uncheckedBorderColor = Color(0xFFEDEDEF),
                                disabledBorderColor = Color.LightGray,
                                disabledUncheckedBorderColor = Color.LightGray,
                                disabledIndeterminateBorderColor = Color.LightGray
                            )
                        )
                    }
                    Spacer(Modifier.width(19.dp))
                    Column(Modifier.clickable(onClick = { switch = !switch })) {
                        Text(
                            "Даю согласие на обработку",
                            color = Color.Black,
                            textDecoration = TextDecoration.Underline
                        )
                        Text(
                            "персональных данных",
                            color = Color.Black,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
                Spacer(Modifier.height(19.dp))
                Buttons(onClick = {
                    viewModel.signUp(
                        context,
                        viewModel.name,
                        viewModel.email,
                        viewModel.password,
                        navController
                    )
                })
            }
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Есть аккаунт?")
                Spacer(Modifier.width(3.dp))
                Text("Войти", Modifier.clickable(onClick = {
                    navController.navigate(Routes.SignIn.route) {
                        popUpTo(Routes.SignIn.route) {
                            inclusive = true
                        }
                    }
                }))
            }
        }
    }
}

@Composable
private fun PdfOpener(pdfState: VerticalPdfReaderState) {
    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
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
            Text("Зарегистрироваться")
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
        Text("Регистрация", fontWeight = FontWeight.Bold, fontSize = 32.sp)
        Text("Заполните Свои Данные Или")
        Text("Продолжите Через Социальные Медиа")
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        SignUpScreen(navController = rememberNavController())
    }
}