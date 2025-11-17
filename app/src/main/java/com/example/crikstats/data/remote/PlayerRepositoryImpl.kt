package com.example.crikstats.data.remote

import com.example.crikstats.domain.model.Player
import com.example.crikstats.domain.repository.CricketApiService
import com.example.crikstats.domain.repository.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val cricketApiService: CricketApiService
): PlayerRepository{

    override suspend fun getPlayers(): Result<List<Player>> =
        try {
            val players = cricketApiService.getPlayers()
            Result.success(players)
        } catch (e: Exception) {
            Result.failure(e)
        }
}