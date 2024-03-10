package me.bitlinker.compose800.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

internal val ThemeColors = compositionLocalOf { Colors.forTheme(false) }

@Composable
internal fun Theme(
    content: @Composable () -> Unit
) {
    val systemIsDark = isSystemInDarkTheme()
    val colors = remember { Colors.forTheme(systemIsDark) }
    CompositionLocalProvider(
        ThemeColors provides colors
    ) {
        content()
    }
}
