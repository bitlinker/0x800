package me.bitlinker.compose800.ui.views

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
import game0x800.composeapp.generated.resources.Res
import game0x800.composeapp.generated.resources.button_new_game_title
import me.bitlinker.compose800.ui.theme.Dimens
import me.bitlinker.compose800.ui.theme.TextStyles
import me.bitlinker.compose800.ui.theme.ThemeColors
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun GameCompletedOverlayView(text: String, onNewGameClick: () -> Unit) {
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
            ButtonView(
                text = stringResource(Res.string.button_new_game_title),
                onClick = onNewGameClick,
            )
        }
    }
}