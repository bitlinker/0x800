package me.bitlinker.compose800.ui.modifiers

import androidx.compose.foundation.focusable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import me.bitlinker.compose800.model.Direction

internal fun Modifier.listenDirectionKeys(onKey: (Direction) -> Unit): Modifier = composed {
    val requester = remember { FocusRequester() }
    var hasFocus by remember { mutableStateOf(false) }
    if (!hasFocus) {
        SideEffect {
            requester.requestFocus()
        }
    }

    this
        .focusRequester(requester)
        .focusable()
        .onFocusChanged { hasFocus = it.isFocused }
        .onKeyEvent {
            if (it.type == KeyEventType.KeyDown) {
                val direction = when (it.key) {
                    Key.DirectionLeft -> Direction.Left
                    Key.DirectionRight -> Direction.Right
                    Key.DirectionDown -> Direction.Down
                    Key.DirectionUp -> Direction.Up
                    else -> null
                }
                if (direction != null) {
                    onKey(direction)
                    return@onKeyEvent true
                }
            }
            return@onKeyEvent false
        }
}
