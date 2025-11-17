package com.example.crikstats.di

import com.example.crikstats.presentation.observer.FeatureDownloadObserver
import com.example.crikstats.presentation.observer.SplitInstallObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ObserverModule {

    @Binds
    @Singleton
    abstract fun bindModuleDownloadObserver(
        impl: SplitInstallObserver
    ): FeatureDownloadObserver
}
