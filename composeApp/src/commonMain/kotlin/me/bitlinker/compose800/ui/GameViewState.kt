package me.bitlinker.compose800.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import me.bitlinker.compose800.model.Cell as ModelCell
import me.bitlinker.compose800.model.GameState

@Immutable
internal data class GameViewState(
    val score: Score,
    val field: Field,
    val state: GameState,
) {
    @Immutable
    data class Score(
        val current: Int,
        val add: Int,
        val high: Int,
    )

    @Immutable
    data class Field(
        val size: IntSize,
        val cells: List<Cell>,
    )

    @Immutable
    data class Cell(
        val cell: ModelCell,
        val position: IntOffset,
    )
}