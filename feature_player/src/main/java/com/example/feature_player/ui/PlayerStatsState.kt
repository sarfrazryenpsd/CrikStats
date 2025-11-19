package com.example.feature_player.ui

import com.example.crikstats.domain.model.Player

sealed class PlayerStatsUiState {
    object Loading : PlayerStatsUiState()
    data class Success(val players: List<Player>) : PlayerStatsUiState()
    data class Error(val message: String) : PlayerStatsUiState()
}