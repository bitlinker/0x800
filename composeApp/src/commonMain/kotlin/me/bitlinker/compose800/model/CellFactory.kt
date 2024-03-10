package me.bitlinker.compose800.model

import kotlinx.serialization.Serializable

@Serializable
internal class CellFactory {
    private var nextId: CellId = 0

    fun create(
        value: Int,
        mergedFromIds: Pair<CellId, CellId>? = null,
    ): Cell {
        return Cell(
            id = nextId++,
            value = value,
            mergedFromIds = mergedFromIds
        )
    }
}