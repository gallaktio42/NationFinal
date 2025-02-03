package com.example.nationfinal.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nationfinal.R
import com.example.nationfinal.Routes
import com.example.nationfinal.model.PopularSneakers
import com.example.nationfinal.ui.theme.NationFinalTheme
import com.example.nationfinal.viewmodel.CardViewModel
import com.example.nationfinal.viewmodel.HomeViewModel
import com.example.nationfinal.viewmodel.PopularViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularScreen(navController: NavController, viewModel: PopularViewModel = viewModel()) {
    val viewModel2: CardViewModel = viewModel()
    val viewModel3: HomeViewModel = viewModel()
    val context = LocalContext.current
    LaunchedEffect(true) {
        try {
            viewModel.data = viewModel.getDataPopular()
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Популярное", fontSize = 16.sp)
                },
                Modifier.padding(horizontal = 20.dp),
                navigationIcon = {
                    Image(
                        painterResource(R.drawable.icon_png_4x),
                        contentDescription = "hamburger",
                        Modifier
                            .size(44.dp)
                            .clickable { navController.navigate(Routes.Home.route) }
                    )
                },
                actions = {
                    Image(painterResource(R.drawable.heart), contentDescription = "bucket")
                }
            )
        },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn() {
                items(viewModel.data.chunked(2)) { item ->
                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                    ) {
                        item.forEach { it3 ->
                            val fav =
                                viewModel3.dataFavorite.find { it.idSneaker == it3.id }
                            val bucket = viewModel3.dataBucket.find { it.idSneaker == it3.id }
                            if (fav != null) {
                                if (bucket != null) {
                                    CardSneakers(
                                        image = it3,
                                        onClick = {
                                            viewModel2.id = it3.id
                                            viewModel2.delete()
                                        },
                                        onBucket = {
                                            viewModel2.id = it3.id
                                            viewModel2.update()
                                        },
                                        icon = R.drawable.iconheart,
                                        buck = R.drawable.addbutton
                                    )
                                    Spacer(Modifier.width(20.dp))
                                } else {
                                    CardSneakers(
                                        image = it3,
                                        onClick = {
                                            viewModel2.id = it3.id
                                            viewModel2.delete()
                                        },
                                        onBucket = {
                                            viewModel2.id = it3.id
                                            viewModel2.insert()
                                        },
                                        icon = R.drawable.iconheart,
                                        buck = R.drawable.bucket
                                    )
                                    Spacer(Modifier.width(20.dp))
                                }
                            } else {
                                if (bucket != null) {
                                    CardSneakers(
                                        image = it3,
                                        onClick = {
                                            viewModel2.id = it3.id
                                            viewModel2.insert2_0()
                                        },
                                        onBucket = {
                                            viewModel2.id = it3.id
                                            viewModel2.update()
                                        },
                                        icon = R.drawable.icon_heart,
                                        buck = R.drawable.addbutton
                                    )
                                    Spacer(Modifier.width(20.dp))
                                } else {
                                    CardSneakers(
                                        image = it3,
                                        onClick = {
                                            viewModel2.id = it3.id
                                            viewModel2.insert2_0()
                                        },
                                        onBucket = {
                                            viewModel2.id = it3.id
                                            viewModel2.insert()
                                        },
                                        icon = R.drawable.icon_heart,
                                        buck = R.drawable.bucket
                                    )
                                    Spacer(Modifier.width(20.dp))
                                }
                            }
                        }
                    }
                    Spacer(Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

/*@Composable
private fun RowCard(image: List<PopularSneakers>, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalArrangement = if (image.size == 1) {
            Arrangement.Start
        } else {
            Arrangement.Center
        }
    ) {
        if (image.isNotEmpty()) {
            CardSneakers(image = image[0], onClick)
        }
        Spacer(Modifier.width(20.dp))
        if (image.size > 1) {
            CardSneakers(image = image[1], onClick)
        }
    }
}*/

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        PopularScreen(navController = rememberNavController())
    }
}