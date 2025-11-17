package com.example.crikstats.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: String,
    val name: String,
    val matches: Int,
    val average: Int
)