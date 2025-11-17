package com.example.crikstats.domain.repository

import com.example.crikstats.domain.model.Player

interface PlayerRepository{
    suspend fun getPlayers(): Result<List<Player>>
}