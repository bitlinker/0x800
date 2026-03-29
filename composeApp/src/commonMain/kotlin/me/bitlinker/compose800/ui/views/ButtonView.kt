package me.bitlinker.compose800.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import me.bitlinker.compose800.ui.theme.Dimens
import me.bitlinker.compose800.ui.theme.TextStyles
import me.bitlinker.compose800.ui.theme.ThemeColors

@Composable
internal fun TextButtonView(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    ButtonView(
        isSelected = isSelected,
        onClick = onClick
    ) {
        val colors = ThemeColors.current
        BasicText(
            text = text,
            style = TextStyles.buttonTextStyle,
            color = { colors.buttonLabel },
        )
    }
}

@Composable
internal fun IconButtonView(
    painter: Painter,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val colors = ThemeColors.current
    ButtonView(
        isSelected = isSelected,
        onClick = onClick
    ) {
        Image(
            painter,
            null,
            colorFilter = ColorFilter.tint(colors.buttonLabel)
        )
    }
}

@Composable
internal fun ButtonView(
    isSelected: Boolean = false,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val colors = ThemeColors.current
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimens.backgroundRadius))
            .clickable(onClick = onClick)
            .background(if (isSelected) colors.buttonSelectedBackground else colors.buttonBackground)
            .padding(
                horizontal = Dimens.paddingMedium,
                vertical = Dimens.paddingSmall
            )
            .defaultMinSize(minHeight = Dimens.paddingLarge)
    ) {
        content()
    }
}