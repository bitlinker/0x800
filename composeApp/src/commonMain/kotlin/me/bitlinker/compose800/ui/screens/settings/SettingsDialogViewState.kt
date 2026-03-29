package me.bitlinker.compose800.ui.screens.settings

import androidx.compose.runtime.Immutable
import me.bitlinker.compose800.repository.ThemeSetting

@Immutable
internal data class SettingsDialogViewState(
    val theme: ThemeSetting,
    val version: String,
)