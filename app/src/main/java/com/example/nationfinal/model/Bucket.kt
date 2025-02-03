package com.example.nationfinal.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bucket(
    val id: String? = null,
    val uuid: String? = null,
    val count: Int,
    @SerialName("id_sneaker")
    val idSneaker: Int
)
