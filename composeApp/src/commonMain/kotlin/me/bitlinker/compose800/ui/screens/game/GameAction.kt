package me.bitlinker.compose800.ui.screens.game

import androidx.compose.runtime.Immutable
import me.bitlinker.compose800.model.Direction

@Immutable
internal sealed interface GameAction {
    data object NewGameClicked : GameAction
    data class Move(val direction: Direction) : GameAction
    data object SettingsClicked: GameAction
}