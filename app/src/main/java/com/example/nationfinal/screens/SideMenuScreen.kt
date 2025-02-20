package com.example.nationfinal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.DeliveryDining
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import coil.compose.AsyncImage
import com.example.nationfinal.R
import com.example.nationfinal.Routes
import com.example.nationfinal.ui.theme.NationFinalTheme
import com.example.nationfinal.viewmodel.SideMenuViewModel

@Composable
fun SideMenuScreen(navController: NavController, viewModel: SideMenuViewModel = viewModel()) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF47ABDE)),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {
        Spacer(Modifier.height(73.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 35.dp)
        ) {
            AsyncImage(
                model = viewModel.imege,
                contentDescription = "lig",
                Modifier.size(96.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(15.dp))
            Text(
                viewModel.nam,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.W700
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Icon(
                Icons.Outlined.PersonOutline,
                contentDescription = "Exit",
                tint = Color.White
            )
            Spacer(Modifier.width(25.dp))
            Text(
                "Профиль",
                Modifier.clickable {
                    navController.navigate("${Routes.BottomBar.route}/${3}")
                },
                color = Color.White
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Icon(
                Icons.Outlined.ShoppingBag,
                contentDescription = "Exit",
                tint = Color.White
            )
            Spacer(Modifier.width(25.dp))
            Text(
                "Корзина",
                Modifier.clickable { navController.navigate(Routes.Bucket.route) },
                color = Color.White
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Icon(
                Icons.Outlined.FavoriteBorder,
                contentDescription = "Exit",
                tint = Color.White
            )
            Spacer(Modifier.width(25.dp))
            Text(
                "Избранное",
                Modifier.clickable {
                    navController.navigate("${Routes.BottomBar.route}/${1}")
                },
                color = Color.White
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Icon(
                Icons.Outlined.DeliveryDining,
                contentDescription = "Exit",
                tint = Color.White
            )
            Spacer(Modifier.width(25.dp))
            Text(
                "Заказы",
                Modifier.clickable { },
                color = Color.White
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Icon(
                Icons.Outlined.Notifications,
                contentDescription = "Exit",
                tint = Color.White
            )
            Spacer(Modifier.width(25.dp))
            Text(
                "Уведомления",
                Modifier.clickable { },
                color = Color.White
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Icon(
                Icons.Outlined.Settings,
                contentDescription = "Exit",
                tint = Color.White
            )
            Spacer(Modifier.width(25.dp))
            Text(
                "Настройки",
                Modifier.clickable { },
                color = Color.White
            )
        }
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Outlined.ExitToApp,
                contentDescription = "Exit",
                tint = Color.White
            )
            Spacer(Modifier.width(25.dp))
            Text(
                "Выйти",
                Modifier.clickable { viewModel.signOut(context, navController) },
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        SideMenuScreen(navController = rememberNavController())
    }
}