package com.example.crikstats.domain.repository

import com.example.crikstats.domain.model.Player

interface CricketApiService {
    suspend fun getPlayers() : List<Player>
}