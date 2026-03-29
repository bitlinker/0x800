package me.bitlinker.compose800.ui.navigation

import androidx.compose.runtime.Immutable
import me.bitlinker.compose800.model.GameState

@Immutable
internal data class AppNavigationState(
    val dialog: AppDialog?,
)

internal sealed interface AppDialog {
    data object Settings : AppDialog
    data class GameCompleted(val gameState: GameState): AppDialog
}