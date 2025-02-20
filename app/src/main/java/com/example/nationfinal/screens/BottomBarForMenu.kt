package com.example.nationfinal.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nationfinal.R
import com.example.nationfinal.Routes
import com.example.nationfinal.ui.theme.NationFinalTheme

@Composable
fun BottomBarForMenu(navController: NavController, index: Int) {

    var selectedIndex by rememberSaveable {
        mutableIntStateOf(index)
    }

    val insets = WindowInsets.navigationBars

    Scaffold(
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(
                        bottom = insets
                            .asPaddingValues()
                            .calculateBottomPadding()
                    )
                    .paint(
                        painter = painterResource(R.drawable.vector_1789),
                        contentScale = ContentScale.FillWidth,
                    )
            ) {
                IconButton(onClick = { selectedIndex = 0 })
                {
                    if (selectedIndex == 0) {
                        Icon(
                            Icons.Outlined.Home,
                            contentDescription = null,
                            tint = Color(0xFF47ABDE)
                        )
                    } else {
                        Icon(
                            Icons.Outlined.Home,
                            contentDescription = null,
                        )
                    }
                }
                IconButton(onClick = { selectedIndex = 1 })
                {
                    if (selectedIndex == 1) {
                        Icon(
                            Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = Color(0xFF47ABDE)
                        )
                    } else {
                        Icon(
                            Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                        )
                    }
                }
                FloatingActionButton(
                    onClick = { navController.navigate(Routes.Bucket.route) },
                    Modifier.offset(y = (-25).dp),
                    shape = RoundedCornerShape(55.dp),
                    containerColor = Color(0xFF47ABDE),
                    contentColor = Color.White
                )
                {
                    Icon(Icons.Outlined.ShoppingBag, contentDescription = null)
                }
                IconButton(onClick = {})
                {
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = null
                    )
                }
                IconButton(onClick = {
                    selectedIndex = 3
                })
                {
                    if (selectedIndex == 3) {
                        Icon(
                            Icons.Outlined.PersonOutline,
                            contentDescription = null,
                            tint = Color(0xFF47ABDE)
                        )
                    } else {
                        Icon(
                            Icons.Outlined.PersonOutline,
                            contentDescription = null
                        )
                    }
                }
            }
        },
        /*content = { padding ->
            ContentScreens(Modifier.padding(padding), selectedIndex, navController)
        }*/
    ) {
        ContentScreen(Modifier.padding(it), selectedIndex, navController)
    }
}

@Composable
private fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    navController: NavController
) {
    Log.d("BottomBar", "SelectedIndex: $selectedIndex")
    when (selectedIndex) {
        0 -> HomeScreen(navController)
        1 -> FavoriteScreen(navController)
        3 -> ProfileScreen(navController)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {

    }
}