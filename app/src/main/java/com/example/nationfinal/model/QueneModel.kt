package com.example.nationfinal.model

import androidx.annotation.DrawableRes

data class QueneModel(
    val id: String,
    val name: String,
    val text: String,
    @DrawableRes val image: Int,
    @DrawableRes val line: Int,
    val textButton: String
)
