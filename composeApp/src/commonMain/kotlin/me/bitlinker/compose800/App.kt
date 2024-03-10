import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.arkivanov.essenty.statekeeper.StateKeeper
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import me.bitlinker.compose800.repository.GameSettingsRepository
import me.bitlinker.compose800.ui.GameAction
import me.bitlinker.compose800.ui.GameScreen
import me.bitlinker.compose800.ui.presentation.GameViewModel
import me.bitlinker.compose800.ui.presentation.GameViewStateMapper
import me.bitlinker.compose800.ui.theme.Theme

@Composable
internal fun App(
    // Override on android only; other platforms have no save state
    stateKeeper: StateKeeper = StateKeeperDispatcher(),
) {
    val viewModel = remember(Unit) {
        GameViewModel(
            stateKeeper = stateKeeper,
            viewStateMapper = GameViewStateMapper(),
            settingsRepository = GameSettingsRepository(),
        )
    }
    val viewState = viewModel.viewStates.collectAsState()

    Theme {
        GameScreen(viewState.value) { action ->
            when (action) {
                GameAction.NewGame -> viewModel.newGame()
                is GameAction.Move -> viewModel.move(action.direction)
            }
        }
    }
}
