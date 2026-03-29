package me.bitlinker.compose800.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import me.bitlinker.compose800.repository.ThemeSetting

internal val ThemeColors = compositionLocalOf { Colors.forTheme(false) }

@Composable
internal fun Theme(
    themeSetting: ThemeSetting,
    content: @Composable () -> Unit,
) {
    val isDark = when (themeSetting) {
        ThemeSetting.Auto -> isSystemInDarkTheme()
        ThemeSetting.Light -> false
        ThemeSetting.Dark -> true
    }
    val colors = remember(isDark) { Colors.forTheme(isDark) }
    CompositionLocalProvider(
        ThemeColors provides colors
    ) {
        content()
    }
}
