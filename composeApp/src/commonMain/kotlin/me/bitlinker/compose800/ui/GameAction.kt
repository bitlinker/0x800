package me.bitlinker.compose800.ui

import androidx.compose.runtime.Immutable
import me.bitlinker.compose800.model.Direction

@Immutable
internal sealed class GameAction {
    data object NewGame : GameAction()
    data class Move(val direction: Direction) : GameAction()
}