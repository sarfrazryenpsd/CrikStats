package com.example.feature_player.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crikstats.domain.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerStatsViewModel @Inject constructor(
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PlayerStatsUiState>(PlayerStatsUiState.Loading)
    val uiState: StateFlow<PlayerStatsUiState> = _uiState.asStateFlow()

    init {
        loadPlayers()
    }

    private fun loadPlayers() {
        viewModelScope.launch {
            _uiState.value = PlayerStatsUiState.Loading

            playerRepository.getPlayers()
                .onSuccess { players ->
                    _uiState.value = PlayerStatsUiState.Success(players)
                }
                .onFailure { error ->
                    _uiState.value = PlayerStatsUiState.Error(
                        error.message ?: "Unknown error occurred"
                    )
                }
        }
    }

    fun retry() {
        loadPlayers()
    }
}