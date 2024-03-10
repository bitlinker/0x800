package me.bitlinker.compose800.ui.views

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.times
import me.bitlinker.compose800.ui.theme.Dimens
import me.bitlinker.compose800.ui.theme.Durations
import me.bitlinker.compose800.ui.theme.TextStyles
import me.bitlinker.compose800.ui.theme.ThemeColors
import me.bitlinker.compose800.ui.utils.hexToString

@Composable
internal fun ScoreView(
    text: String,
    value: Int,
    addValue: Int,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    color = ThemeColors.current.scoreBackground,
                    shape = RoundedCornerShape(Dimens.scoreBackgroundCornerRadius)
                )
                .padding(
                    horizontal = Dimens.paddingMedium,
                    vertical = Dimens.paddingExtraSmall
                )
                .widthIn(min = Dimens.scoreMinWidth)
        ) {
            Title(text)
            ValueView(value)
        }
        if (addValue > 0) {
            AddValueAnimationView(
                updateKey = value,
                addValue = addValue,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun Title(text: String) {
    val colors = ThemeColors.current
    BasicText(
        text = text.uppercase(),
        style = TextStyles.scoreTitleTextStyle,
        color = { colors.scoreLabel }
    )
}

@Composable
private fun ValueView(value: Int) {
    val colors = ThemeColors.current
    val scaleAnimation = remember(value) {
        Animatable(0F)
    }
    LaunchedEffect(value) {
        scaleAnimation.animateTo(1F, tween(Durations.scoreChange, easing = EaseOutBack))
    }
    BasicText(
        text = value.hexToString(3),
        style = TextStyles.scoreValueTextStyle,
        color = { colors.scoreValueLabel },
        modifier = Modifier
            .scale(scaleAnimation.value * 0.1F + 0.9F)
    )
}

@Composable
private fun AddValueAnimationView(
    updateKey: Int,
    addValue: Int,
    modifier: Modifier
) {
    val colors = ThemeColors.current
    val animationState = remember(updateKey) { Animatable(0F) }
    LaunchedEffect(updateKey) {
        animationState.animateTo(
            1F,
            tween(durationMillis = Durations.scoreAddChange, easing = LinearEasing)
        )
    }
    if (animationState.value < 1F) {
        BasicText(
            text = "+${addValue.hexToString()}",
            style = TextStyles.scoreAddValueTextStyle,
            color = { colors.scoreValueAddLabel },
            modifier = modifier
                .offset(y = -animationState.value * Dimens.scoreAddValueMaxOffset)
                .alpha(1F - animationState.value)
        )
    }
}
