package com.example.crikstats.presentation.state

sealed class DownloadState {
    object NotStarted : DownloadState()
    object Pending : DownloadState()
    data class Downloading(val progress: Int) : DownloadState()
    object Installing : DownloadState()
    object Success : DownloadState()
    object AlreadyInstalled : DownloadState()
    data class Error(val message: String) : DownloadState()
}