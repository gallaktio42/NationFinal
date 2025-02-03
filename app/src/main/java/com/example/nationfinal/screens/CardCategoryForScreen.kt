package com.example.nationfinal.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.nationfinal.ui.theme.NationFinalTheme
import okhttp3.internal.wait

@Composable
fun CardCategoryForScreen(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        Modifier
            .width(108.dp)
            .height(40.dp)
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF47ABDE) else Color.White
        )
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text, color = if (isSelected) Color.White else Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NationFinalTheme {
        CardCategory(navController = rememberNavController(), text = "Joe")
    }
}