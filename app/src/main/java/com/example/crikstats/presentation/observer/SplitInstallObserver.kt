package com.example.crikstats.presentation.observer

import com.example.crikstats.presentation.state.DownloadState
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SplitInstallObserver @Inject constructor(
    private val splitInstallManager: SplitInstallManager
): FeatureDownloadObserver{

    private val _state = MutableStateFlow<DownloadState>(DownloadState.NotStarted)
    override val downloadState: Flow<DownloadState> = _state

    private val listener = SplitInstallStateUpdatedListener { state ->
        when (state.status()) {
            SplitInstallSessionStatus.DOWNLOADING -> {
                val progress = if (state.totalBytesToDownload() > 0) {
                    (state.bytesDownloaded() * 100 / state.totalBytesToDownload()).toInt()
                } else 0
                _state.value = DownloadState.Downloading(progress)
            }
            SplitInstallSessionStatus.INSTALLING -> _state.value = DownloadState.Installing
            SplitInstallSessionStatus.INSTALLED -> _state.value = DownloadState.Success
            SplitInstallSessionStatus.FAILED -> _state.value =
                DownloadState.Error("Download failed: ${state.errorCode()}")
            SplitInstallSessionStatus.CANCELED -> _state.value =
                DownloadState.Error("Download cancelled")
            SplitInstallSessionStatus.PENDING -> _state.value = DownloadState.Pending
        }
    }

    init{
        splitInstallManager.registerListener(listener)
    }

    override fun unregister() =
        splitInstallManager.unregisterListener(listener)

}