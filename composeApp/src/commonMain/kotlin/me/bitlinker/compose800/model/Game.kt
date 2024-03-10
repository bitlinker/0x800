package me.bitlinker.compose800.model

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
internal data class GameConfig(
    val size: Vec2 = Vec2(4, 4),
    val initialCells: Int = 2,
    val newCellValueFactory: () -> Int = { if (Random.nextFloat() < 0.9F) 1 else 2 },
    val winValue: Int = 11,
) {
    init {
        require(size.x * size.y > initialCells)
    }
}

@Serializable
internal class Game(
    private val gameConfig: GameConfig,
    private val cellFactory: CellFactory,
) {
    val field: Field = Field(gameConfig.size)

    var gameState: GameState = GameState.Normal
        private set

    var score: Int = 0
        private set

    init {
        if (field.isEmpty()) {
            initialSpawn()
        }
    }

    fun reset() {
        gameState = GameState.Normal
        score = 0
        field.clear()
        initialSpawn()
    }

    private fun initialSpawn() {
        for (i in 0 until gameConfig.initialCells) {
            spawn()
        }
    }

    private fun spawn() {
        val pos = field.randomEmptyCellPosition()
        require(pos != null)
        val cell = cellFactory.create(gameConfig.newCellValueFactory())
        field[pos] = cell
    }

    fun update(direction: Direction): Boolean {
        if (gameState != GameState.Normal) return false
        if (field.countEmptyCells() == 0) {
            gameState = GameState.Loose
            return true
        }

        val isChanged = field.updateRowsInDirection(direction)
        if (isChanged) {
            spawn()
        }
        return true
    }

    private fun moveRow(originalCells: List<Cell?>): List<Cell?> {
        val resultCells = mutableListOf<Cell?>()
        var isLastCellMerged = false
        originalCells.forEach { cell ->
            if (cell != null) {
                val lastResult = resultCells.lastOrNull()
                if (lastResult?.value == cell.value && !isLastCellMerged) {
                    // Merge
                    val newCell = cellFactory.create(
                        value = cell.value + 1,
                        mergedFromIds = lastResult.id to cell.id
                    )
                    isLastCellMerged = true
                    resultCells.removeLast()
                    resultCells.add(newCell)
                    score += newCell.value.pow2Of()
                    if (newCell.value == gameConfig.winValue) {
                        gameState = GameState.Win
                    }
                } else {
                    // Move
                    isLastCellMerged = false
                    resultCells.add(cell)
                }
            }
        }
        while (resultCells.size < originalCells.size) {
            resultCells.add(null)
        }
        return resultCells
    }

    private fun Field.updateRowsInDirection(direction: Direction): Boolean {
        val rowsPositions = directionInteractionPositions(direction)
        var isChanged = false
        rowsPositions.forEach { positions ->
            val originalCells = positions.map { get(it) }
            val newCells = moveRow(originalCells)
            if (originalCells != newCells) {
                positions.forEachIndexed { index, position ->
                    field[position] = if (index < newCells.size) newCells[index] else null
                }
                isChanged = true
            }
        }
        return isChanged
    }

    private data class DirectionIterationParams(
        val start: Vec2,
        val offset: Vec2,
        val rowSize: Int,
    )

    private fun Field.directionInteractionPositions(direction: Direction): List<List<Vec2>> {
        return when (direction) {
            Direction.Left ->
                (0 until size.y).map { y ->
                    DirectionIterationParams(
                        start = Vec2(0, y),
                        offset = Vec2(1, 0),
                        rowSize = size.x,
                    )
                }

            Direction.Right ->
                (0 until size.y).map { y ->
                    DirectionIterationParams(
                        start = Vec2(size.x - 1, y),
                        offset = Vec2(-1, 0),
                        rowSize = size.x,
                    )
                }

            Direction.Down ->
                (0 until size.x).map { x ->
                    DirectionIterationParams(
                        start = Vec2(x, size.y - 1),
                        offset = Vec2(0, -1),
                        rowSize = size.y,
                    )
                }

            Direction.Up ->
                (0 until size.x).map { x ->
                    DirectionIterationParams(
                        start = Vec2(x, 0),
                        offset = Vec2(0, 1),
                        rowSize = size.y,
                    )
                }
        }.map { it.positions() }
    }

    private fun DirectionIterationParams.positions(): List<Vec2> {
        return buildList {
            for (i in 0..<rowSize) {
                add(start + offset * i)
            }
        }
    }
}