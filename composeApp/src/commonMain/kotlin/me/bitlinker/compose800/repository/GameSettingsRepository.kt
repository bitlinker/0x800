package me.bitlinker.compose800.repository

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getIntFlow
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal enum class ThemeSetting {
    Auto,
    Light,
    Dark,
}

internal class GameSettingsRepository(
    private val settings: ObservableSettings,
) {

    var highScore: Int = settings[KEY_HIGHSCORE] ?: 0
        set(value) {
            if (field != value) {
                field = value
                settings[KEY_HIGHSCORE] = value
            }
        }

    var theme: ThemeSetting
        set(value) {
            settings[KEY_THEME] = value.toInt()
        }
        get() = (settings[KEY_THEME] ?: 0).toThemeSetting()

    val themeChanges: Flow<ThemeSetting> = settings
        .getIntFlow(KEY_THEME, 0)
        .map { it.toThemeSetting() }

    private fun Int.toThemeSetting(): ThemeSetting {
        return ThemeSetting.entries[this]
    }

    private fun ThemeSetting.toInt(): Int {
        return this.ordinal
    }

    companion object {
        private const val KEY_HIGHSCORE = "highscore"
        private const val KEY_THEME = "theme"
    }
}