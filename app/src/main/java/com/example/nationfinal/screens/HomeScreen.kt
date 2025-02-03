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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nationfinal.R
import com.example.nationfinal.Routes
import com.example.nationfinal.ui.theme.NationFinalTheme
import com.example.nationfinal.viewmodel.CardViewModel
import com.example.nationfinal.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val viewModel2: CardViewModel = viewModel()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Главная", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                },
                Modifier.padding(horizontal = 20.dp),
                navigationIcon = {
                    Image(
                        painterResource(R.drawable.hamburger),
                        contentDescription = "hamburger"
                    )
                },
                actions = {
                    Image(painterResource(R.drawable.group_27), contentDescription = "bucket")
                }
            )
        },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),

            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                Button(
                    onClick = {navController.navigate(Routes.Search.route)},
                    Modifier
                        .height(52.dp)
                        .width(277.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 5.dp
                    )
                ) {
                    Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Search, contentDescription = "search", tint = Color(0xFF6a6a6a))
                        Spacer(Modifier.padding(horizontal = 7.dp))
                        Text("Поиск", color = Color(0xFF6a6a6a))
                    }
                }
                /**/
                Spacer(Modifier.padding(horizontal = 7.dp))
                Box(
                    Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(55.dp))
                        .background(Color(0xFF47ABDE)), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painterResource(R.drawable.sliders),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }


            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
            ) {
                Text("Категории", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(25.dp))
                LazyRow {
                    items(viewModel.category) {
                        Column(Modifier.padding(end = 16.dp)) {
                            CardCategory(navController, it)
                        }
                    }
                }
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                ) {
                    Text("Популярное")
                    Spacer(Modifier.weight(1f))
                    Text(
                        "Все",
                        Modifier.clickable(onClick = { navController.navigate(Routes.Popular.route) }),
                        color = Color(0xFF47ABDE),
                        fontSize = 12.sp,
                    )
                }
                Spacer(Modifier.height(25.dp))
                if (viewModel.data.isEmpty()) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .width(175.dp)
                            .height(200.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyRow {
                        items(viewModel.data.take(3), key = { it.id }) { item ->
                            val fav = viewModel.dataFavorite.find { it.idSneaker == item.id }
                            val bucket = viewModel.dataBucket.find { it.idSneaker == item.id }
                            if (fav != null) {
                                if (bucket != null) {
                                    Column(Modifier.padding(end = 22.dp)) {
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
                                    }
                                } else {
                                    Column(Modifier.padding(end = 22.dp)) {
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
                                }
                            } else {
                                if (bucket != null) {
                                    Column(Modifier.padding(end = 22.dp)) {
                                        CardSneakers(
                                            image = item,
                                            onClick = {
                                                viewModel2.id = item.id
                                                viewModel2.insert2_0()
                                            },
                                            onBucket = {
                                                viewModel2.id = item.id
                                                viewModel2.update()
                                            },
                                            icon = R.drawable.icon_heart,
                                            buck = R.drawable.addbutton
                                        )
                                    }
                                } else {
                                    Column(Modifier.padding(end = 22.dp)) {
                                        CardSneakers(
                                            image = item,
                                            onClick = {
                                                viewModel2.id = item.id
                                                viewModel2.insert2_0()
                                            },
                                            onBucket = {
                                                viewModel2.id= item.id
                                                viewModel2.insert()
                                            },
                                            icon = R.drawable.icon_heart,
                                            buck = R.drawable.bucket
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                ) {
                    Text("Акции")
                    Spacer(Modifier.weight(1f))
                    Text("Все", color = Color(0xFF47ABDE), fontSize = 12.sp)
                }
                Spacer(Modifier.height(25.dp))
                CardSale()
            }
            /*LazyColumn {
            items(1) {
                RowCard()
            }
        }
        Text("Home")
        Button(
            onClick = { viewModel.signOut(context, navController) },
        )
        { Text("Log Out") }*/
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        HomeScreen(navController = rememberNavController())
    }
}