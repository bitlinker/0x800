package me.bitlinker.compose800.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Dialog
import me.bitlinker.compose800.di.AppComponentDependencies
import me.bitlinker.compose800.di.AppComponent
import me.bitlinker.compose800.repository.ThemeSetting
import me.bitlinker.compose800.ui.navigation.AppDialog
import me.bitlinker.compose800.ui.screens.game.GameAction
import me.bitlinker.compose800.ui.screens.game.GameScreen
import me.bitlinker.compose800.ui.screens.game_completed.GameCompletedDialogScreen
import me.bitlinker.compose800.ui.screens.settings.SettingsDialogAction
import me.bitlinker.compose800.ui.screens.settings.SettingsDialogScreen
import me.bitlinker.compose800.ui.theme.Theme

@Composable
fun App(deps: AppComponentDependencies) {
    val appComponent = remember { AppComponent(deps) }
    val settingsRepository = remember { appComponent.settingsRepository }
    val navigationState by appComponent.appNavigator.state.collectAsState()
    val themes by settingsRepository.themeChanges.collectAsState(ThemeSetting.Auto)

    Theme(themeSetting = themes) {
        val gameViewModel = remember(Unit) {
            appComponent.gameViewModel()
        }
        val gameViewState by gameViewModel.viewStates.collectAsState()
        GameScreen(state = gameViewState, dispatcher = gameViewModel::dispatch)

        AnimatedVisibility(
            visible = navigationState.dialog != null,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            when (val dialog = navigationState.dialog) {
                AppDialog.Settings -> {
                    val settingsDialogViewModel = remember(Unit) {
                        appComponent.settingsDialogViewModel()
                    }
                    val settingsDialogViewState by settingsDialogViewModel.viewStates.collectAsState()
                    Dialog(
                        onDismissRequest = { settingsDialogViewModel.dispatch(SettingsDialogAction.OnDismissed) },
                        content = {
                            SettingsDialogScreen(
                                state = settingsDialogViewState,
                                dispatcher = settingsDialogViewModel::dispatch,
                            )
                        }
                    )
                }

                is AppDialog.GameCompleted -> {
                    val gameCompletedViewModel = remember(Unit) {
                        appComponent.gameCompletedViewModel(
                            gameState = dialog.gameState,
                            onNewGameClicked = { gameViewModel.dispatch(GameAction.NewGameClicked) },
                        )
                    }
                    GameCompletedDialogScreen(gameCompletedViewModel)
                }

                null -> Unit
            }
        }
    }
}
