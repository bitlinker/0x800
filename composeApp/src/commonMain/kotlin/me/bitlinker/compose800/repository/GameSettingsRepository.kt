package me.bitlinker.compose800.repository

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

internal class GameSettingsRepository(
    private val settings: Settings = Settings()
) {
    var highScore: Int = settings[KEY_HIGHSCORE] ?: 0
        set(value) {
            if (field != value) {
                field = value
                settings[KEY_HIGHSCORE] = value
            }
        }

    companion object {
        private const val KEY_HIGHSCORE = "highscore"
    }
}