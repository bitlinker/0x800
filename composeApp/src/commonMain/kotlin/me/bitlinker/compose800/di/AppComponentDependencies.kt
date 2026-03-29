package me.bitlinker.compose800.di

import com.arkivanov.essenty.statekeeper.StateKeeper
import com.russhwolf.settings.ObservableSettings

data class AppComponentDependencies(
    val settings: ObservableSettings,
    val urlOpener: UrlOpener,
    val stateKeeper: StateKeeper,
)