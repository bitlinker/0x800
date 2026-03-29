package me.bitlinker.compose800.ui.screens.game_completed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import me.bitlinker.compose800.composeapp.generated.resources.*
import me.bitlinker.compose800.model.GameState
import me.bitlinker.compose800.ui.theme.Dimens
import me.bitlinker.compose800.ui.theme.TextStyles
import me.bitlinker.compose800.ui.theme.ThemeColors
import me.bitlinker.compose800.ui.views.TextButtonView
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun GameCompletedDialogScreen(viewModel: GameCompletedViewModel) {

    val text = when (viewModel.state) {
        GameState.Win -> stringResource(Res.string.game_completed_text_game_win)
        GameState.Loose -> stringResource(Res.string.game_completed_text_game_over)
        GameState.Normal -> return
    }

    val colors = ThemeColors.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.75F)
                .background(ThemeColors.current.frameBackground)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicText(
                text = text,
                style = TextStyles.titleTextStyle,
                color = { colors.titleLabel }
            )
            TextButtonView(
                text = stringResource(Res.string.game_button_new_game_title),
                onClick = { viewModel.dispatch(GameCompletedAction.NewGameClicked) },
            )
        }
    }
}