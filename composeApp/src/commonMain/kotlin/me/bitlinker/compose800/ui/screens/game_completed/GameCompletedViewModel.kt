package me.bitlinker.compose800.ui.screens.game_completed

import me.bitlinker.compose800.model.GameState
import me.bitlinker.compose800.ui.navigation.AppNavigator

internal class GameCompletedViewModel(
    gameState: GameState,
    private val appNavigator: AppNavigator,
    private val onNewGameClicked: () -> Unit,
) {

    val state = gameState

    fun dispatch(action: GameCompletedAction) {
        when (action) {
            is GameCompletedAction.NewGameClicked -> {
                onNewGameClicked()
                appNavigator.dismissDialog()
            }
        }
    }
}
