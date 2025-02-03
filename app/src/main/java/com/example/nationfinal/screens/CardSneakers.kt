package com.example.nationfinal.screens

import android.graphics.drawable.PaintDrawable
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.nationfinal.R
import com.example.nationfinal.model.PopularSneakers
import com.example.nationfinal.ui.theme.NationFinalTheme
import com.example.nationfinal.viewmodel.CardViewModel

@Composable
fun CardSneakers(
    image: PopularSneakers,
    onClick: () -> Unit,
    onBucket: () -> Unit,
    @DrawableRes icon: Int,
    @DrawableRes buck: Int
) {
    //val icon = if (!image.isFav) R.drawable.icon_heart else R.drawable.iconheart
    Card(
        Modifier
            .width(175.dp)
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 9.dp, vertical = 10.dp)
        ) {
            Image(
                painterResource(icon), contentDescription = "like", Modifier
                    .clickable(onClick = onClick)
                    .size(28.dp)
            )
        }
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = image.image,
                contentDescription = null,
                Modifier
                    .graphicsLayer(scaleX = -1f)
                    .width(118.dp)
                    .height(70.dp),
                contentScale = ContentScale.Crop
            )
            /*Image(
                painterResource(R.drawable.nike), contentDescription = "nike",
                Modifier
                    .width(148.dp)
                    .height(73.dp)
            )*/
        }
        Column(
            Modifier
                .fillMaxHeight()
                .padding(start = 9.dp)
        ) {
            if (image.best) {
                Text("BEST SELLER", fontSize = 12.sp, color = Color(0xFF47ABDE))
            } else {
                Text("           ", fontSize = 12.sp, color = Color(0xFF47ABDE))
            }
            Text(image.name, fontSize = 16.sp)
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("₽${image.price}", fontSize = 14.sp)
                Modifier.weight(1f)
                Image(
                    painterResource(buck),
                    contentDescription = "add",
                    Modifier.clickable(onClick = onBucket)
                )
            }
        }
    }
}

@Preview()
@Composable
private fun Preview() {
    NationFinalTheme {
        //CardSneakers(image = )
    }
}
