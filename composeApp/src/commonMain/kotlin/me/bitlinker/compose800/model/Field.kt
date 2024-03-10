package me.bitlinker.compose800.model

import kotlinx.serialization.Serializable

@Serializable
internal class Field(
    val size: Vec2,
    private val cells: MutableList<Cell?> = MutableList(size.x * size.y) { null },
) {
    operator fun get(position: Vec2): Cell? {
        return cells[position.index]
    }

    operator fun set(position: Vec2, value: Cell?) {
        cells[position.index] = value
    }

    fun isEmpty(): Boolean {
        return !cells.any { it != null }
    }

    fun countEmptyCells(): Int {
        return cells.count { it == null }
    }

    fun randomEmptyCellPosition(): Vec2? {
        return cells
            .mapIndexedNotNull { index, cell -> if (cell == null) index else null }
            .randomOrNull()
            ?.let { index -> Vec2(index % size.x, index / size.x) }
    }

    fun clear() {
        for (index in cells.indices) {
            cells[index] = null
        }
    }

    private val Vec2.index: Int
        get() {
            val index = x + y * size.x
            require(index >= 0 && index < size.x * size.y)
            return index
        }
}
