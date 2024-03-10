package me.bitlinker.compose800.ui.modifiers

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import me.bitlinker.compose800.model.Direction
import kotlin.math.abs

internal fun Modifier.listenDirectionDrags(onDrag: (Direction) -> Unit): Modifier {
    return pointerInput(Unit) {
        var startOffset = Offset(0F, 0F)
        var lastOffset = Offset(0F, 0F)
        detectDragGestures(
            onDragStart = {
                startOffset = it
            },
            onDragEnd = {
                val dragAmount = lastOffset - startOffset
                val direction = if (abs(dragAmount.x) > abs(dragAmount.y)) {
                    if (dragAmount.x > 0F) {
                        Direction.Right
                    } else {
                        Direction.Left
                    }
                } else {
                    if (dragAmount.y > 0F) {
                        Direction.Down
                    } else {
                        Direction.Up
                    }
                }
                onDrag(direction)
            }
        ) { change, _ ->
            lastOffset = change.position
        }
    }
}
