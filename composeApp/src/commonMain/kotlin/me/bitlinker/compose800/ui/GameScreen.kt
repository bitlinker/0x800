package me.bitlinker.compose800.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import game0x800.composeapp.generated.resources.Res
import game0x800.composeapp.generated.resources.button_new_game_title
import game0x800.composeapp.generated.resources.text_app_title
import game0x800.composeapp.generated.resources.text_game_description
import game0x800.composeapp.generated.resources.text_game_over
import game0x800.composeapp.generated.resources.text_game_win
import game0x800.composeapp.generated.resources.text_highscore_title
import game0x800.composeapp.generated.resources.text_score_title
import me.bitlinker.compose800.model.GameState
import me.bitlinker.compose800.ui.theme.Dimens
import me.bitlinker.compose800.ui.theme.TextStyles
import me.bitlinker.compose800.ui.theme.ThemeColors
import me.bitlinker.compose800.ui.utils.parseMarkdownToAnnotatedString
import me.bitlinker.compose800.ui.views.ButtonView
import me.bitlinker.compose800.ui.views.GameCompletedOverlayView
import me.bitlinker.compose800.ui.views.GameFieldView
import me.bitlinker.compose800.ui.views.ScoreView
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun GameScreen(state: GameViewState, dispatcher: (GameAction) -> Unit) {
    BoxWithConstraints {
        if (maxHeight >= maxWidth) {
            PortraitScreen(state, dispatcher)
        } else {
            LandscapeScreen(state, dispatcher)
        }
        OptionalGameCompletedOverlay(state.state, dispatcher)
    }
}

@Composable
private fun PortraitScreen(state: GameViewState, dispatcher: (GameAction) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(ThemeColors.current.frameBackground)
            .safeDrawingPadding()
            .padding(Dimens.paddingLarge)
    ) {
        Spacer(Modifier.weight(1F))
        PortraitHeader(state.score, dispatcher)
        Spacer(Modifier.height(Dimens.paddingLarge))
        GameFieldView(
            state.field,
            dispatcher,
        )
        Spacer(Modifier.weight(1F))
    }
}

@Composable
private fun LandscapeScreen(state: GameViewState, dispatcher: (GameAction) -> Unit) {
    Row(
        verticalAlignment = Top,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .background(ThemeColors.current.frameBackground)
            .safeDrawingPadding()
            .padding(Dimens.paddingLarge)
    ) {
        LandscapeHeader(state, dispatcher)
        Spacer(modifier = Modifier.width(Dimens.paddingLarge))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            GameFieldView(
                field = state.field,
                dispatcher = dispatcher,
            )
        }
    }
}

@Composable
private fun PortraitHeader(
    score: GameViewState.Score,
    dispatcher: (GameAction) -> Unit,
) {
    Column {
        Row(
            verticalAlignment = CenterVertically,
        ) {
            Title()
            Spacer(
                Modifier
                    .weight(1F)
                    .widthIn(min = Dimens.paddingSmall)
            )
            CurrentScore(score)
            Spacer(Modifier.width(Dimens.paddingExtraSmall))
            HighScore(score)
        }
        Spacer(Modifier.height(Dimens.paddingSmall))
        Row(
            verticalAlignment = CenterVertically,
        ) {
            Description()
            Spacer(Modifier.weight(1F))
            NewGameButton(dispatcher)
        }
    }
}

@Composable
private fun LandscapeHeader(
    state: GameViewState,
    dispatcher: (GameAction) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)) {
        Title()
        Description()
        Row(horizontalArrangement = Arrangement.spacedBy(Dimens.paddingExtraSmall)) {
            CurrentScore(state.score)
            HighScore(state.score)
        }
        NewGameButton(dispatcher)
    }
}

@Composable
private fun Title() {
    val colors = ThemeColors.current
    BasicText(
        text = stringResource(Res.string.text_app_title),
        style = TextStyles.titleTextStyle,
        color = { colors.titleLabel }
    )
}

@Composable
private fun CurrentScore(score: GameViewState.Score) {
    ScoreView(
        text = stringResource(Res.string.text_score_title),
        value = score.current,
        addValue = score.add,
        modifier = Modifier
    )
}

@Composable
private fun HighScore(score: GameViewState.Score) {
    ScoreView(
        text = stringResource(Res.string.text_highscore_title),
        value = score.high,
        addValue = 0,
        modifier = Modifier
    )
}

@Composable
private fun NewGameButton(dispatcher: (GameAction) -> Unit) {
    ButtonView(stringResource(Res.string.button_new_game_title)) {
        dispatcher(GameAction.NewGame)
    }
}

@Composable
private fun Description() {
    val colors = ThemeColors.current
    val text = stringResource(Res.string.text_game_description)
    val annotatedString = remember { text.parseMarkdownToAnnotatedString() }
    BasicText(
        text = annotatedString,
        style = TextStyles.descriptionTextStyle,
        color = { colors.titleLabel },
    )
}

@Composable
private fun OptionalGameCompletedOverlay(state: GameState, dispatcher: (GameAction) -> Unit) {
    val stateString = when (state) {
        GameState.Win -> stringResource(Res.string.text_game_win)
        GameState.Loose -> stringResource(Res.string.text_game_over)
        GameState.Normal -> null
    }
    AnimatedVisibility(
        visible = stateString != null,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        val string = remember { stateString!! }
        GameCompletedOverlayView(
            text = string,
            onNewGameClick = { dispatcher(GameAction.NewGame) })
    }
}
