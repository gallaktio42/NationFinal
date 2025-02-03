package com.example.nationfinal.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchHistory(
    val id: Int? = null,
    val uuid: String? = null,
    val query: String? = null
)
