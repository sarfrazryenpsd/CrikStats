package com.example.crikstats.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json{
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
        }

    @Provides
    @Singleton
    fun provideHttpClient(json: Json): HttpClient =
        HttpClient(Android){
            install(ContentNegotiation){
                json(json)
            }
            install(Logging){
                logger = Logger.ANDROID
                level = LogLevel.BODY
            }
            engine {
                connectTimeout = 30_000
                socketTimeout = 30_000
            }
        }

    @Provides
    @Singleton
    fun provideBaseUrl(): String =
        "https://691a5ff82d8d7855756e8ad8.mockapi.io/crikstats"

}