package me.bitlinker.compose800.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal interface Colors {
    val frameBackground: Color
    val frameBody: Color
    val frameEmptyCell: Color

    val titleLabel: Color

    val scoreLabel: Color
    val scoreValueLabel: Color
    val scoreValueAddLabel: Color
    val scoreBackground: Color

    val buttonBackground: Color
    val buttonLabel: Color

    val cellColors: List<Pair<Color, Color>>
    val cellShadow: Color

    companion object {
        fun forTheme(isDark: Boolean): Colors {
            return if (isDark) DarkColors else LightColors
        }
    }
}

private data object LightColors : Colors {
    override val frameBackground = Color(0xFFFAF8EF)
    override val frameBody = Color(0xFFBCAC9F)
    override val frameEmptyCell = Color(0xFFCCC1B4)

    override val titleLabel: Color = Color(0xFF776E65)

    override val scoreLabel: Color = Color(0xFFEEE4DA)
    override val scoreValueLabel: Color = Color(0xFFF9F6F2)
    override val scoreValueAddLabel: Color = Color(0xFF8F7A66)
    override val scoreBackground: Color = Color(0xFFBBADA0)

    override val buttonBackground: Color = Color(0xFF8F7A66)
    override val buttonLabel: Color = Color(0xFFF9F6F2)

    private val textDarkColor = Color(0XFF786F66)
    private val textLightColor = Color(0XFFF9F6F2)
    override val cellColors: List<Pair<Color, Color>> = listOf(
        Color(0XFFEEE4DA) to textDarkColor,
        Color(0XFFEDE0C8) to textDarkColor,
        Color(0XFFF2B179) to textDarkColor,
        Color(0xFFFF9060) to textDarkColor,
        Color(0XFFF67C5F) to textDarkColor,
        Color(0XFFF65E3B) to textDarkColor,
        Color(0XFFEDCF72) to textLightColor,
        Color(0XFFEDCC61) to textLightColor,
        Color(0XFFEDC850) to textLightColor,
        Color(0XFFEDC53F) to textLightColor,
        Color(0XFFEDC22E) to textLightColor,
    )
    override val cellShadow = Color(0xFF111111)
}

private data object DarkColors : Colors {
    override val frameBackground = Color(0XFF1A1B21)
    override val frameBody = Color(0XFF435160)
    override val frameEmptyCell = Color(0XFF283340)

    override val titleLabel: Color = Color(0XFF88919A)

    override val scoreLabel: Color = Color(0XFF111A26)
    override val scoreValueLabel: Color = Color(0XFF04090C)
    override val scoreValueAddLabel: Color = Color(0XFF88919A)
    override val scoreBackground: Color = Color(0XFF43515F)

    override val buttonBackground: Color = Color(0XFF70859A)
    override val buttonLabel: Color = Color(0XFF04090C)

    private val textDarkColor = Color(0XFF868F9A)
    private val textLightColor = Color(0XFF050C21)
    override val cellColors: List<Pair<Color, Color>> = listOf(
        Color(0XFF101A26) to textDarkColor,
        Color(0XFF121E38) to textDarkColor,
        Color(0XFF0D4E86) to textLightColor,
        Color(0xFF006B9A) to textDarkColor,
        Color(0XFF0883A0) to textLightColor,
        Color(0XFF08A0C5) to textLightColor,
        Color(0XFF12308D) to textLightColor,
        Color(0XFF12339E) to textLightColor,
        Color(0XFF1237B0) to textLightColor,
        Color(0XFF1239BE) to textLightColor,
        Color(0XFF113DD1) to textLightColor,
    )
    override val cellShadow = Color(0XFF1A1B21)
}

internal object Dimens {
    val cellBorder = 5.dp
    val backgroundRadius = 8.dp
    val cellRadius = 4.dp
    val cellElevation = 4.dp

    val fontHuge = 42.sp
    val fontCell = 16.sp
    val fontMedium = 14.sp
    val fontSmall = 12.sp

    val paddingLarge = 16.dp
    val paddingMedium = 12.dp
    val paddingSmall = 8.dp
    val paddingExtraSmall = 6.dp

    val scoreAddValueMaxOffset = 30.dp
    val scoreBackgroundCornerRadius = 6.dp
    val scoreMinWidth = 50.dp
}

internal object Durations {
    const val scoreChange = 300
    const val scoreAddChange = 400

    const val cellSpawn = 200
    const val cellHide = 200
    const val cellMove = 160
}

internal object TextStyles {
    val titleTextStyle = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = Dimens.fontHuge,
    )

    val descriptionTextStyle = TextStyle(
        fontSize = Dimens.fontMedium,
    )

    val scoreTitleTextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = Dimens.fontSmall,
    )

    val scoreValueTextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = Dimens.fontMedium,
    )

    val scoreAddValueTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = Dimens.fontMedium,
    )

    val buttonTextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = Dimens.fontMedium,
    )

    val cellTextStyle = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = Dimens.fontCell,
    )
}