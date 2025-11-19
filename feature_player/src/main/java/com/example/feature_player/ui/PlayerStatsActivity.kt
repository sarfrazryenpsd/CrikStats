package com.example.feature_player.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.example.crikstats.FeatureEntryPoint
import com.example.crikstats.domain.repository.PlayerRepository
import com.example.feature_player.di.DaggerFeatureComponent
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class PlayerStatsActivity : ComponentActivity() {

    @Inject
    lateinit var playerRepository: PlayerRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        val featureDeps = EntryPointAccessors.fromApplication(
            applicationContext,
            FeatureEntryPoint::class.java
        )

        // Build the Dagger component for this feature and inject dependencies
        val component = DaggerFeatureComponent.builder()
            .appDependencies(featureDeps)
            .build()
        component.inject(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                PlayerStatsScreen(
                    onBackClick = { finish() }
                )
            }
        }
    }
}