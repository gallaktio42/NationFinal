package com.example.nationfinal.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nationfinal.model.PopularSneakers
import com.example.nationfinal.ui.theme.NationFinalTheme

@Composable
fun CardBucket(
    image: PopularSneakers, count: Int?, onDelete: () -> Unit,
    onPlus: () -> Unit,
    onMinus: () -> Unit
) {
    var clickText by remember { mutableStateOf(false) }
    var clickIcon by remember { mutableStateOf(false) }
    Card(
        Modifier
            .fillMaxWidth()
            .height(104.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = if (clickText || clickIcon) Arrangement.SpaceBetween else Arrangement.spacedBy(
                33.dp
            )
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp)
                    .clickable {
                        clickIcon = !clickIcon
                        clickText = false
                    }
            ) {
                Column(
                    Modifier
                        .width(87.dp)
                        .height(85.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray),
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = image.image,
                        contentDescription = "crocs",
                        Modifier
                            .offset(y = 5.dp)
                            .width(86.dp)
                            .height(55.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Column(
                Modifier
                    .fillMaxHeight()
                    .clickable {
                        clickText = !clickText
                        clickIcon = false
                    },
                verticalArrangement = Arrangement.Center,
            ) {
                Text(image.name)
                Text("₽${image.price}")
            }
            if (clickText) {
                Spacer(Modifier.padding(horizontal = 13.dp))
                Column(
                    Modifier
                        .width(58.dp)
                        .height(104.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Red),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = onDelete
                    ) {
                        Icon(
                            Icons.Default.RestoreFromTrash,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
            if (clickIcon) {
                Spacer(Modifier.padding(horizontal = 13.dp))
                Column(
                    Modifier
                        .width(58.dp)
                        .height(104.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF47ABDE)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("+", Modifier.clickable (onClick = onPlus), color = Color.White)
                    Spacer(Modifier.padding(vertical = 7.dp))
                    Text("$count", color = Color.White)
                    Spacer(Modifier.padding(vertical = 7.dp))
                    Text("-", Modifier.clickable (onClick = onMinus), color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        //CardBucket(image = PopularSneakers(1, name = keke, true, price = 753.34,  ))
    }
}