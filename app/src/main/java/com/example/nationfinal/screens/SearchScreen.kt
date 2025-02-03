package com.example.nationfinal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nationfinal.R
import com.example.nationfinal.Routes
import com.example.nationfinal.model.SearchHistory
import com.example.nationfinal.ui.theme.NationFinalTheme
import com.example.nationfinal.viewmodel.HomeViewModel
import com.example.nationfinal.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = viewModel()) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Поиск", fontSize = 16.sp)
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
            )
        },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                query = viewModel.text,
                onQueryChange = {
                    viewModel.text = it
                },
                onSearch = {
                    viewModel.active = false
                    viewModel.filteredData = viewModel.search(it)
                    viewModel.insert(it)
                },
                active = viewModel.active,
                onActiveChange = {
                    viewModel.active = it
                },
                Modifier
                    .offset(y = (-15).dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text("Поиск")
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Search, contentDescription = "search")
                },
                shape = RoundedCornerShape(12.dp),
                colors = SearchBarDefaults.colors(
                    containerColor = Color.White,
                ),
                shadowElevation = 5.dp
            ) {
                LazyColumn {
                    items(viewModel.history) { text ->
                        HistoryRow(text, onClick = { viewModel.text = text.query.toString() })
                        Spacer(Modifier.padding(horizontal = 17.dp))
                    }
                }
            }
            if (viewModel.text.isEmpty()) {
                LazyColumn {
                    items(viewModel.data.chunked(2)) { item ->
                        Row(Modifier.fillMaxSize()) {
                            item.forEach { card ->
                                val fav = viewModel.dataFavorite.find { it.idSneaker == card.id }
                                val bucket = viewModel.dataBucket.find { it.idSneaker == card.id }
                                if (fav != null) {
                                    if (bucket != null) {
                                        CardSneakers(
                                            image = card,
                                            onClick = {

                                            },
                                            onBucket = {

                                            },
                                            icon = R.drawable.iconheart,
                                            buck = R.drawable.addbutton
                                        )
                                        Spacer(Modifier.width(20.dp))
                                    } else {
                                        CardSneakers(
                                            image = card,
                                            onClick = {

                                            },
                                            onBucket = {

                                            },
                                            icon = R.drawable.iconheart,
                                            buck = R.drawable.bucket
                                        )
                                        Spacer(Modifier.width(20.dp))
                                    }
                                } else {
                                    if (bucket != null) {
                                        CardSneakers(
                                            image = card,
                                            onClick = {

                                            },
                                            onBucket = {

                                            },
                                            icon = R.drawable.icon_heart,
                                            buck = R.drawable.addbutton
                                        )
                                        Spacer(Modifier.width(20.dp))
                                    } else {
                                        CardSneakers(
                                            image = card,
                                            onClick = {

                                            },
                                            onBucket = {

                                            },
                                            icon = R.drawable.icon_heart,
                                            buck = R.drawable.bucket
                                        )
                                        Spacer(Modifier.width(20.dp))
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.padding(vertical = 10.dp))
                    }
                }
            } else {
                LazyColumn {
                    items(viewModel.filteredData.chunked(2)) { item ->
                        Row(Modifier.fillMaxSize()) {
                            item.forEach { card ->
                                val fav = viewModel.dataFavorite.find { it.idSneaker == card.id }
                                val bucket = viewModel.dataBucket.find { it.idSneaker == card.id }
                                if (fav != null) {
                                    if (bucket != null) {
                                        CardSneakers(
                                            image = card,
                                            onClick = {

                                            },
                                            onBucket = {

                                            },
                                            icon = R.drawable.iconheart,
                                            buck = R.drawable.addbutton
                                        )
                                        Spacer(Modifier.width(20.dp))
                                    } else {
                                        CardSneakers(
                                            image = card,
                                            onClick = {

                                            },
                                            onBucket = {

                                            },
                                            icon = R.drawable.iconheart,
                                            buck = R.drawable.bucket
                                        )
                                        Spacer(Modifier.width(20.dp))
                                    }
                                } else {
                                    if (bucket != null) {
                                        CardSneakers(
                                            image = card,
                                            onClick = {

                                            },
                                            onBucket = {

                                            },
                                            icon = R.drawable.icon_heart,
                                            buck = R.drawable.addbutton
                                        )
                                        Spacer(Modifier.width(20.dp))
                                    } else {
                                        CardSneakers(
                                            image = card,
                                            onClick = {

                                            },
                                            onBucket = {

                                            },
                                            icon = R.drawable.icon_heart,
                                            buck = R.drawable.bucket
                                        )
                                        Spacer(Modifier.width(20.dp))
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.padding(vertical = 10.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryRow(text: SearchHistory, onClick: (String?) -> Unit) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
            .clickable(onClick = { onClick(text.query) }),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.padding(vertical = 17.dp))
        Icon(Icons.Outlined.AccessTime, contentDescription = "history")
        Spacer(Modifier.padding(horizontal = 7.dp))
        Text("${text.query}")
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        SearchScreen(navController = rememberNavController())
    }
}