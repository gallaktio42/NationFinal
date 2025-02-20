package com.example.nationfinal.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpTable(
    val id: String? = null,
    val name: String,
    val email: String,
    val surname: String? = "",
    val address: String? = "",
    val number: String? = "",
)
