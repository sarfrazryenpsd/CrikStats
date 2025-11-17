package com.example.crikstats.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crikstats.presentation.observer.FeatureDownloadObserver
import com.example.crikstats.presentation.state.DownloadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val observer: FeatureDownloadObserver
): ViewModel(){

    private val _uiState = MutableStateFlow<DownloadState>(DownloadState.NotStarted)
    val uiState: StateFlow<DownloadState> = _uiState

    init {
        observe()
    }

    private fun observe(){
        viewModelScope.launch {
            observer.downloadState.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun resetState() {
        _uiState.value = DownloadState.NotStarted
    }

    override fun onCleared() {
        super.onCleared()
        observer.unregister()
    }
}