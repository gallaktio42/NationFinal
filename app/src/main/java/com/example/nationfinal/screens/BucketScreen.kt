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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
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
import com.example.nationfinal.ui.theme.NationFinalTheme
import com.example.nationfinal.viewmodel.BucketViewModel
import com.example.nationfinal.viewmodel.CardBucketViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BucketScreen(navController: NavController, viewModel: BucketViewModel = viewModel()) {
    val viewModel2: CardBucketViewModel = viewModel()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Корзина", fontSize = 16.sp)
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
            )
        },
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text("${viewModel.count} товара")
                }
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(viewModel.data) { index, item ->
                        val count = viewModel.dataFavorite.find { it.idSneaker == item.id }?.count
                        CardBucket(image = item,
                            count,
                            onDelete = {
                                viewModel.id = item.id
                                viewModel.delete()
                            },
                            onPlus = {
                                viewModel.id = item.id
                                viewModel.insert()
                            },
                            onMinus = {
                                viewModel.id = item.id
                                viewModel.insertReverse()
                            })
                        if (viewModel.data.lastIndex == index) {
                            Spacer(Modifier.padding(vertical = 135.dp))
                        } else {
                            Spacer(Modifier.padding(vertical = 10.dp))
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 35.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Сумма и Доставка
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Сумма")
                    Text(text = "₽${viewModel.price}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Доставка")
                    Text(text = "₽60.20")
                }

                HorizontalDivider()

                // Итог
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Итого",
                    )
                    Text(
                        text = "₽${viewModel.all}",
                    )
                }
                Spacer(Modifier.padding(bottom = 17.dp))
                // Кнопка
                Button(
                    onClick = { /* Обработка клика */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF47ABDE)
                    )
                ) {
                    Text(text = "Оформить Заказ")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        BucketScreen(navController = rememberNavController())
    }
}