package me.bitlinker.compose800.ui.views

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInQuint
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import me.bitlinker.compose800.model.pow2Of
import me.bitlinker.compose800.ui.theme.Dimens
import me.bitlinker.compose800.ui.theme.Durations
import me.bitlinker.compose800.ui.theme.TextStyles
import me.bitlinker.compose800.ui.theme.ThemeColors
import me.bitlinker.compose800.ui.utils.hexToString

@Composable
internal fun CellView(
    value: Int,
    position: IntOffset,
    isHiding: Boolean,
    size: DpSize,
    onHideCompleted: () -> Unit,
) {
    val alphaAnimation = remember { Animatable(0F) }
    val scaleAnimation = remember { Animatable(0F) }
    LaunchedEffect(Unit) {
        alphaAnimation.animateTo(1F, tween(Durations.cellSpawn / 2, easing = LinearEasing))
        scaleAnimation.animateTo(1F, tween(Durations.cellSpawn, easing = EaseOutBack))
    }

    val hiding = animateFloatAsState(
        targetValue = if (isHiding) 0F else 1F,
        animationSpec = tween(Durations.cellHide, easing = EaseInQuint),
        finishedListener = { if (it == 0F) onHideCompleted() }
    )

    val animatedPosition = animateSizeAsState(
        targetValue = Size(position.x.toFloat(), position.y.toFloat()),
        animationSpec = tween(Durations.cellMove, easing = EaseInOut),
    )

    if (hiding.value > 0F) {
        CellContentView(
            value = value,
            modifier = Modifier
                .size(size)
                .offset(
                    x = size.width * animatedPosition.value.width,
                    y = size.height * animatedPosition.value.height,
                )
                .scale(scaleAnimation.value)
                .alpha(alphaAnimation.value * hiding.value)
        )
    }
}

@Composable
private fun CellContentView(
    value: Int,
    modifier: Modifier = Modifier,
) {
    val (bgColor, fontColor) = ThemeColors.current.cellColors[value - 1]
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1F)
            .padding(Dimens.cellBorder)
            .shadow(
                elevation = Dimens.cellElevation,
                ambientColor = ThemeColors.current.cellShadow,
                spotColor = ThemeColors.current.cellShadow,
            )
            .zIndex(value.toFloat())
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    color = bgColor,
                    shape = RoundedCornerShape(Dimens.cellRadius)
                )
        )
        val expandedValue = value.pow2Of()

        BasicText(
            text = expandedValue.hexToString(),
            style = TextStyles.cellTextStyle,
            color = { fontColor },
            maxLines = 1,
        )
    }
}
