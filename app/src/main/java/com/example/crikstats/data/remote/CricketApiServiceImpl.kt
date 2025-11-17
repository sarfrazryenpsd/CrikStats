package com.example.crikstats.data.remote

import com.example.crikstats.domain.repository.CricketApiService
import com.example.crikstats.domain.model.Player
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class CricketApiServiceImpl @Inject constructor(
    private val baseUrl: String,
    private val httpClient: HttpClient
) : CricketApiService{

    override suspend fun getPlayers(): List<Player> =
        httpClient.get("$baseUrl/players").body()
}