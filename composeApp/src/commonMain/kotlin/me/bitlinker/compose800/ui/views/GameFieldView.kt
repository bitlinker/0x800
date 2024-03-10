package me.bitlinker.compose800.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import me.bitlinker.compose800.model.Cell
import me.bitlinker.compose800.model.CellId
import me.bitlinker.compose800.ui.GameAction
import me.bitlinker.compose800.ui.GameViewState
import me.bitlinker.compose800.ui.modifiers.listenDirectionDrags
import me.bitlinker.compose800.ui.modifiers.listenDirectionKeys
import me.bitlinker.compose800.ui.theme.Dimens
import me.bitlinker.compose800.ui.theme.ThemeColors

@Composable
internal fun GameFieldView(
    field: GameViewState.Field,
    dispatcher: (GameAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(field.size.width.toFloat() / field.size.height)
            .background(
                color = ThemeColors.current.frameBody,
                shape = RoundedCornerShape(Dimens.backgroundRadius)
            )
            .listenDirectionDrags { dispatcher(GameAction.Move(it)) }
            .listenDirectionKeys { dispatcher(GameAction.Move(it)) }
            .padding(Dimens.cellBorder)

    ) {
        val cellSize = remember { mutableStateOf(DpSize(0.dp, 0.dp)) }
        val cellSizeModifier = with(LocalDensity.current) {
            Modifier
                .onSizeChanged {
                    cellSize.value = it.toSize().toDpSize()
                }
        }
        Column {
            repeat(field.size.height) { y ->
                Row(Modifier.weight(1F)) {
                    repeat(field.size.width) { x ->
                        val isFirstCell = x == 0 && y == 0
                        Box(Modifier.weight(1F)) {
                            CellBackgroundView(if (isFirstCell) cellSizeModifier else Modifier)
                        }
                    }
                }
            }
        }

        Cells(
            cellSize = cellSize.value,
            cells = field.cells,
        )
    }
}

@Immutable
private data class UiCellState(
    val cell: Cell,
    val position: IntOffset,
    val isMerging: Boolean,
)

@Composable
private fun Cells(
    cellSize: DpSize,
    cells: List<GameViewState.Cell>,
) {
    val cellsUiStates = remember { mutableStateMapOf<CellId, UiCellState>() }

    LaunchedEffect(cells) {
        updateUiCellsState(cellsUiStates, cells)
    }

    cellsUiStates.values.forEach { cell ->
        key(cell.cell.id) {
            CellView(
                value = cell.cell.value,
                position = cell.position,
                isHiding = cell.isMerging,
                size = cellSize,
                onHideCompleted = {
                    cellsUiStates.remove(cell.cell.id)
                }
            )
        }
    }
}

private fun updateUiCellsState(
    uiState: MutableMap<CellId, UiCellState>,
    cells: List<GameViewState.Cell>
) {
    val newCellIdsWithHiding = mutableSetOf<CellId>()
    cells.forEach { cell ->
        uiState.getOrPut(cell.cell.id) {
            UiCellState(
                cell = cell.cell,
                position = cell.position,
                isMerging = false,
            )
        }
        uiState.updateIfExists(cell.cell.id) {
            it.copy(position = cell.position)
        }
        newCellIdsWithHiding.add(cell.cell.id)
        cell.cell.mergedFromIds?.let { (id1, id2) ->
            uiState.updateIfExists(id1) {
                it.copy(
                    position = cell.position,
                    isMerging = true
                )
            }
            uiState.updateIfExists(id2) {
                it.copy(
                    position = cell.position,
                    isMerging = true
                )
            }
            newCellIdsWithHiding.add(id1)
            newCellIdsWithHiding.add(id2)
        }
    }
    val removedIds = uiState.keys - newCellIdsWithHiding
    removedIds.forEach { uiState.remove(it) }
}

private inline fun <K, V> MutableMap<K, V>.updateIfExists(key: K, crossinline block: (V) -> V) {
    get(key)?.let { value ->
        put(key, block(value))
    }
}

@Composable
private fun CellBackgroundView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(1F)
            .padding(Dimens.cellBorder)
            .background(
                color = ThemeColors.current.frameEmptyCell,
                shape = RoundedCornerShape(Dimens.cellRadius)
            )
    )
}
