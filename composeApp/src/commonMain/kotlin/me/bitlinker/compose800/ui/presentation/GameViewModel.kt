package me.bitlinker.compose800.ui.presentation

import com.arkivanov.essenty.statekeeper.StateKeeper
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.bitlinker.compose800.repository.GameSettingsRepository
import me.bitlinker.compose800.model.CellFactory
import me.bitlinker.compose800.model.GameConfig
import me.bitlinker.compose800.model.Direction
import me.bitlinker.compose800.model.Game
import me.bitlinker.compose800.ui.GameViewState

internal class GameViewModel(
    stateKeeper: StateKeeper,
    private val viewStateMapper: GameViewStateMapper,
    private val settingsRepository: GameSettingsRepository,
) {
    private var game: Game =
        stateKeeper.consume(key = KEY_GAME, strategy = Game.serializer()) ?: createGame()

    init {
        stateKeeper.register(key = KEY_GAME, strategy = Game.serializer()) {
            Napier.d { "Saving game" }
            game
        }
    }

    private val mutableViewState = MutableStateFlow(viewStateMapper.createViewState(
        game,
        highScore = settingsRepository.highScore
    ))
    val viewStates: StateFlow<GameViewState> = mutableViewState

    fun newGame() {
        game.reset()
        updateViewState()
    }

    fun move(direction: Direction) {
        val prevScore = game.score
        if (game.update(direction)) {
            if (game.score > settingsRepository.highScore) {
                settingsRepository.highScore = game.score
            }
            updateViewState(prevScore)
        }
    }

    private fun createGame(): Game {
        Napier.d { "Creating game" }
        return Game(
            gameConfig = GameConfig(),
            cellFactory = CellFactory(),
        )
    }

    private fun updateViewState(prevScore: Int? = null) {
        mutableViewState.value = viewStateMapper.createViewState(game, prevScore, settingsRepository.highScore)
    }

    companion object {
        private const val KEY_GAME = "game"
    }
}