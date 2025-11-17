package com.example.crikstats.presentation.observer

import com.example.crikstats.presentation.state.DownloadState
import kotlinx.coroutines.flow.Flow

interface FeatureDownloadObserver{
    val downloadState: Flow<DownloadState>
    fun unregister()
}