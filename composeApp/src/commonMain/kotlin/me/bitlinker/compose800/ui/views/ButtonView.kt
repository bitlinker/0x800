package me.bitlinker.compose800.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import me.bitlinker.compose800.ui.theme.Dimens
import me.bitlinker.compose800.ui.theme.TextStyles
import me.bitlinker.compose800.ui.theme.ThemeColors

@Composable
internal fun ButtonView(text: String, onClick: () -> Unit) {
    val colors = ThemeColors.current
    BasicText(
        text = text,
        style = TextStyles.buttonTextStyle,
        color = { colors.buttonLabel },
        modifier = Modifier
            .clip(RoundedCornerShape(Dimens.backgroundRadius))
            .background(colors.buttonBackground)
            .clickable(onClick = onClick)
            .padding(
                horizontal = Dimens.paddingMedium,
                vertical = Dimens.paddingSmall
            )
    )
}