package com.example.nationfinal.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nationfinal.R
import com.example.nationfinal.Routes
import com.example.nationfinal.model.Favorite
import com.example.nationfinal.model.PopularSneakers
import com.example.nationfinal.ui.theme.NationFinalTheme
import com.example.nationfinal.viewmodel.CardViewModel
import com.example.nationfinal.viewmodel.FavoriteViewModel
import com.example.nationfinal.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController, viewModel: FavoriteViewModel = viewModel()) {
    val viewModel2: CardViewModel = viewModel()
    val viewModel3: HomeViewModel = viewModel()
    val context = LocalContext.current
    LaunchedEffect(true) {
        try {
            viewModel.data = viewModel.getFavoriteSneakers()
            Log.d("pen", "${viewModel.data}")
        } catch (e: Exception) {
            Log.d("MyErorr", "${e.message}")
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Избранное", fontSize = 16.sp)
                },
                Modifier.padding(horizontal = 20.dp),
                navigationIcon = {
                    Image(
                        painterResource(R.drawable.icon_png_4x),
                        contentDescription = "hamburger",
                        Modifier
                            .size(44.dp)
                            .clickable { navController.navigate(Routes.Home.route) },
                    )
                },
                actions = {
                    Image(painterResource(R.drawable.heart), contentDescription = "bucket")
                }
            )
        },
    ) { padding ->
        /*LazyColumn(Modifier.padding(bottom = 89.dp)) {
            items(viewModel.data.chunked(2)) {
                Spacer(Modifier.padding(vertical = 7.dp))
            }
        }*/
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn {
                itemsIndexed(viewModel.data.chunked(2)) { index, pair ->
                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                    ) {
                        pair.forEach { item ->
                            val bucket = viewModel3.dataBucket.find { it.idSneaker == item.id }
                            if (bucket!=null) {
                                CardSneakers(
                                    image = item,
                                    onClick = {
                                        viewModel2.id = item.id
                                        viewModel2.delete()
                                    },
                                    onBucket = {
                                        viewModel2.id = item.id
                                        viewModel2.update()
                                    },
                                    icon = R.drawable.iconheart,
                                    buck = R.drawable.addbutton
                                )
                            }else{
                                CardSneakers(
                                    image = item,
                                    onClick = {
                                        viewModel2.id = item.id
                                        viewModel2.delete()
                                    },
                                    onBucket = {
                                        viewModel2.id = item.id
                                        viewModel2.insert()
                                    },
                                    icon = R.drawable.iconheart,
                                    buck = R.drawable.bucket
                                )
                            }
                            Spacer(Modifier.width(20.dp)) // Отступ между элементами
                        }
                    }
                    if (index == viewModel.data.chunked(2).lastIndex) {
                        Spacer(Modifier.padding(vertical = 49.dp))
                    }
                    else{
                        Spacer(Modifier.padding(vertical = 9.dp))
                    }
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
        FavoriteScreen(navController = rememberNavController())
    }
}