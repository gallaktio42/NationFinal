package com.example.nationfinal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nationfinal.R
import com.example.nationfinal.ui.theme.NationFinalTheme

@Composable
fun CardSale() {
    Card(
        Modifier
            .fillMaxWidth()
            .height(95.dp), colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(R.drawable.sale),
                contentDescription = null,
                Modifier
                    .width(131.dp)
                    .height(55.dp)
            )
            Image(
                painterResource(R.drawable.nikezoom),
                contentDescription = null,
                Modifier
                    .width(87.dp)
                    .height(77.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        CardSale()
    }
}