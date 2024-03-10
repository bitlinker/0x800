package me.bitlinker.compose800.model

import kotlinx.serialization.Serializable

typealias CellId = Int

@Serializable
internal data class Cell(
    val id: CellId,
    val value: Int, // power of 2
    val mergedFromIds: Pair<CellId, CellId>?,
)
