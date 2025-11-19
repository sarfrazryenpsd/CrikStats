package com.example.feature_player.di

import com.example.crikstats.FeatureEntryPoint
import com.example.feature_player.ui.PlayerStatsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [FeatureEntryPoint::class])
interface FeatureComponent {
    fun inject(activity: PlayerStatsActivity)

    @Component.Builder
    interface Builder {
        fun appDependencies(deps: FeatureEntryPoint): Builder
        fun build(): FeatureComponent
    }
}