package me.bitlinker.compose800.ui.presentation

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import me.bitlinker.compose800.model.Field
import me.bitlinker.compose800.model.Game
import me.bitlinker.compose800.model.Vec2
import me.bitlinker.compose800.ui.GameViewState

internal class GameViewStateMapper {
    fun createViewState(game: Game, prevScore: Int? = null, highScore: Int): GameViewState {
        return GameViewState(
            score = GameViewState.Score(
                current = game.score,
                add = prevScore?.let { game.score - it } ?: 0,
                high = highScore,
            ),
            field = GameViewState.Field(
                size = IntSize(game.field.size.x, game.field.size.y),
                cells = mapCells(game.field),
            ),
            state = game.gameState,
        )
    }

    private fun mapCells(field: Field): List<GameViewState.Cell> {
        val result = mutableListOf<GameViewState.Cell>()
        for (y in 0 until field.size.y) {
            for (x in 0 until field.size.x) {
                val pos = Vec2(x, y)
                val cell = field[pos]
                if (cell != null) {
                    result.add(
                        GameViewState.Cell(
                            cell = cell,
                            position = IntOffset(x, y)
                        )
                    )
                }
            }
        }
        return result
    }
}