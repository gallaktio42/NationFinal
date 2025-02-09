package com.example.nationfinal.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nationfinal.Routes
import com.example.nationfinal.viewmodel.OnBoardViewModel

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun OnBoardScreen(navController: NavController, viewModel: OnBoardViewModel = viewModel()) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF47ABDE)),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        if (viewModel.isFirstView) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "${viewModel.currentItem?.name}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W900,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 45.sp
                )
            }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(viewModel.currentItem!!.image),
                    contentDescription = null,
                    Modifier
                        .width(400.dp)
                        .height(302.dp)
                        .offset(11.dp)
                )
                Spacer(Modifier.height(40.dp))
                Image(
                    painter = painterResource(viewModel.currentItem!!.line),
                    contentDescription = null,
                    Modifier
                        .width(123.dp)
                        .height(10.dp)
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { viewModel.next() },
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("${viewModel.currentItem?.textButton}")
                }
            }
        } else {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(viewModel.currentItem!!.image),
                    contentDescription = null,
                    Modifier
                        .width(400.dp)
                        .height(302.dp)
                        .offset(11.dp)
                )
            }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "${viewModel.currentItem?.name}",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.W700,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 45.sp
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "${viewModel.currentItem?.text}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
                Spacer(Modifier.height(40.dp))
                Image(
                    painter = painterResource(viewModel.currentItem!!.line),
                    contentDescription = null,
                    Modifier
                        .width(123.dp)
                        .height(10.dp)
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (viewModel.quene.isEmpty()) {
                            navController.navigate(Routes.SignIn.route)
                        } else {
                            viewModel.next()
                        }
                    },
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("${viewModel.currentItem?.textButton}")
                }
            }
        }
    }
}


/*Button(onClick = {navController.navigate(Routes.SignIn.route){
            popUpTo(Routes.SignIn.route){
                inclusive = true
            }
        } }) { Text("Back") }*/