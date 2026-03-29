package me.bitlinker.compose800.ui.screens.game_completed

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface GameCompletedAction {
    data object NewGameClicked : GameCompletedAction
}