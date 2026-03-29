package me.bitlinker.compose800.di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import me.bitlinker.compose800.BuildKonfig
import me.bitlinker.compose800.model.GameState
import me.bitlinker.compose800.repository.GameSettingsRepository
import me.bitlinker.compose800.ui.navigation.AppNavigator
import me.bitlinker.compose800.ui.screens.game.GameViewModel
import me.bitlinker.compose800.ui.screens.game.GameViewStateMapper
import me.bitlinker.compose800.ui.screens.game_completed.GameCompletedViewModel
import me.bitlinker.compose800.ui.screens.settings.SettingsDialogViewModel

internal class AppComponent(
    private val deps: AppComponentDependencies,
) {

    init {
        if (BuildKonfig.isDebug) {
            Napier.base(DebugAntilog())
        }
    }

    val appNavigator by lazy {
        AppNavigator()
    }

    fun gameViewModel(): GameViewModel {
        return GameViewModel(
            stateKeeper = deps.stateKeeper,
            viewStateMapper = GameViewStateMapper(),
            settingsRepository = settingsRepository,
            navigator = appNavigator,
        )
    }

    fun settingsDialogViewModel(): SettingsDialogViewModel {
        return SettingsDialogViewModel(
            settingsRepository = settingsRepository,
            navigator = appNavigator,
            urlOpener = deps.urlOpener,
        )
    }

    fun gameCompletedViewModel(
        gameState: GameState,
        onNewGameClicked: () -> Unit,
    ): GameCompletedViewModel {
        return GameCompletedViewModel(
            gameState = gameState,
            onNewGameClicked = onNewGameClicked,
            appNavigator = appNavigator,
        )
    }

    val settingsRepository by lazy {
        GameSettingsRepository(deps.settings)
    }
}