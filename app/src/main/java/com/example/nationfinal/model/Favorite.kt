package com.example.nationfinal.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Favorite(
    val id: String? = null,
    val uuid: String? = null,
    @SerialName("id_sneaker")
    val idSneaker: Int
)
