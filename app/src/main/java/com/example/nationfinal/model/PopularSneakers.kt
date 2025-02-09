package com.example.nationfinal.model

import kotlinx.serialization.Serializable

@Serializable
data class PopularSneakers(
    val id: Int,
    val name: String,
    val best: Boolean,
    val price: Float,
    val image: String,
    val category: String,
)
