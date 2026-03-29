package me.bitlinker.compose800.ui.screens.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.bitlinker.compose800.BuildKonfig
import me.bitlinker.compose800.di.UrlOpener
import me.bitlinker.compose800.repository.GameSettingsRepository
import me.bitlinker.compose800.ui.navigation.AppNavigator

internal class SettingsDialogViewModel(
    private val settingsRepository: GameSettingsRepository,
    private val navigator: AppNavigator,
    private val urlOpener: UrlOpener,
) {

    private val mutableViewStates = MutableStateFlow(createState())

    val viewStates: StateFlow<SettingsDialogViewState> = mutableViewStates

    fun dispatch(action: SettingsDialogAction) {
        when (action) {
            is SettingsDialogAction.OnPrivacyPolicyClicked -> {
                urlOpener.open(BuildKonfig.privacyPolicyUrl)
            }

            is SettingsDialogAction.OnSourcesClicked -> {
                urlOpener.open(BuildKonfig.github)
            }

            is SettingsDialogAction.OnThemeChanged -> {
                settingsRepository.theme = action.theme
                updateState()
            }

            is SettingsDialogAction.OnDismissed -> navigator.dismissDialog()
        }
    }

    private fun updateState() {
        mutableViewStates.value = createState()
    }

    private fun createState() = SettingsDialogViewState(
        theme = settingsRepository.theme,
        version = BuildKonfig.version,
    )
}