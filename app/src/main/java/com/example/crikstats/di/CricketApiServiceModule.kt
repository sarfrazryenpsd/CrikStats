package com.example.crikstats.di

import com.example.crikstats.data.remote.CricketApiServiceImpl
import com.example.crikstats.domain.repository.CricketApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CricketApiServiceModule{

    @Provides
    @Singleton
    fun provideCricketApiServiceModule(
        baseUrl: String,
        httpClient: HttpClient
    ): CricketApiService = CricketApiServiceImpl(baseUrl, httpClient)



}