package me.bitlinker.compose800.ui.screens.settings

import androidx.compose.runtime.Immutable
import me.bitlinker.compose800.repository.ThemeSetting

@Immutable
internal sealed interface SettingsDialogAction {
    data class OnThemeChanged(val theme: ThemeSetting) : SettingsDialogAction
    data object OnPrivacyPolicyClicked : SettingsDialogAction
    data object OnSourcesClicked : SettingsDialogAction
    data object OnDismissed : SettingsDialogAction
}