package com.example.nationfinal.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import okhttp3.internal.notifyAll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    navController: NavController, category: String, viewModel: HomeViewModel = viewModel()
) {
    val viewModel2: CardViewModel = viewModel()
    var selectedCategory by rememberSaveable { mutableStateOf(category) }
    val filtered = viewModel.dataAll.filter { it.category == selectedCategory }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(selectedCategory, fontSize = 16.sp)
                },
                Modifier.padding(horizontal = 20.dp),
                navigationIcon = {
                    Image(painterResource(R.drawable.icon_png_4x),
                        contentDescription = "hamburger",
                        Modifier
                            .size(44.dp)
                            .clickable { navController.navigate(Routes.Home.route) })
                },
            )
        },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Spacer(Modifier.height(15.dp))
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
                            CardCategoryForScreen(it,
                                isSelected = selectedCategory == it,
                                onClick = {
                                    selectedCategory = it
                                })
                        }
                    }
                }
            }
            Spacer(Modifier.height(15.dp))
            Column(Modifier.fillMaxWidth()) {
                if (selectedCategory == "All") {
                    LazyColumn {
                        items(viewModel.dataAll.chunked(2)) { pair ->
                            Row(
                                Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp)
                            ) {
                                pair.forEach { item ->
                                    val fav =
                                        viewModel.dataFavorite.find { it.idSneaker == item.id }
                                    val bucket =
                                        viewModel.dataBucket.find { it.idSneaker == item.id }
                                    if (fav != null) {
                                        if (bucket != null) {
                                            CardSneakers(
                                                image = item, onClick = {
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
                                            Spacer(Modifier.width(20.dp)) // Отступ между элементами
                                        } else {
                                            CardSneakers(
                                                image = item, onClick = {
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
                                            Spacer(Modifier.width(20.dp))
                                        }
                                    } else {
                                        if (bucket != null) {
                                            CardSneakers(
                                                image = item, onClick = {
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
                                            Spacer(Modifier.width(20.dp))
                                        } else {
                                            CardSneakers(
                                                image = item, onClick = {
                                                    viewModel2.id = item.id
                                                    viewModel2.insert2_0()
                                                },
                                                onBucket = {
                                                    viewModel2.id = item.id
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
                            Spacer(Modifier.padding(vertical = 8.dp)) // Отступ между строками
                        }
                    }
                } else if (filtered.isEmpty()) {
                    Text(
                        "Empty", Modifier.fillMaxWidth(), textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn {
                        items(filtered.chunked(2)) { pair ->
                            Row(
                                Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp)
                            ) {
                                pair.forEach { item ->
                                    val fav =
                                        viewModel.dataFavorite.find { it.idSneaker == item.id }
                                    val bucket =
                                        viewModel.dataBucket.find { it.idSneaker == item.id }
                                    if (fav != null) {
                                        if (bucket != null) {
                                            CardSneakers(
                                                image = item, onClick = {
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
                                            Spacer(Modifier.width(20.dp)) // Отступ между элементами
                                        }
                                        else{
                                            CardSneakers(
                                                image = item, onClick = {
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
                                            Spacer(Modifier.width(20.dp))
                                        }
                                    } else {
                                        if (bucket !=null) {
                                            CardSneakers(
                                                image = item, onClick = {
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
                                            Spacer(Modifier.width(20.dp)) // Отступ между элементами
                                        }else{
                                            CardSneakers(
                                                image = item, onClick = {
                                                    viewModel2.id = item.id
                                                    viewModel2.insert2_0()
                                                },
                                                onBucket = {
                                                    viewModel2.id = item.id
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
                            Spacer(Modifier.padding(vertical = 8.dp)) // Отступ между строками
                        }
                    }
                }
                /*if (selectedCategory == "All") {
                    LazyColumn {
                        items(5) {
                            Spacer(Modifier.padding(vertical = 7.dp))
                            RowCard()
                        }
                    }
                } else if (selectedCategory == "Outdoor") {
                    LazyColumn {
                        items(2) {
                            Spacer(Modifier.padding(vertical = 7.dp))
                            RowCard()
                        }
                    }
                } else if (selectedCategory == "Tennis") {
                    Text(
                        "Empty", Modifier.fillMaxWidth(), textAlign = TextAlign.Center
                    )
                }*/
            }
        }
    }
}

/*@Composable
private fun RowCard(image: List<PopularSneakers>) {
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
            CardSneakers(image = image[0], onClick={

            })
        }
        Spacer(Modifier.width(20.dp))
        if (image.size > 1) {
            CardSneakers(image = image[1], onClick = {

            })
        }
    }
}*/

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        CategoryScreen(navController = rememberNavController(), category = "Outdoor")
    }
}