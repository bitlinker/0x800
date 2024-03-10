package me.bitlinker.compose800.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight

internal fun String.parseMarkdownToAnnotatedString(): AnnotatedString {
    val spanStyles = mutableListOf<AnnotatedString.Range<SpanStyle>>()
    var boldOpenedOffset: Int? = null
    var sizeReduction = 0
    val clearString = replace("\\*\\*".toRegex()) { matchResult ->
        boldOpenedOffset?.let {
            spanStyles.add(
                AnnotatedString.Range(
                    item = SpanStyle(fontWeight = FontWeight.Bold),
                    start = it,
                    end = matchResult.range.first - sizeReduction,
                )
            )
            boldOpenedOffset = null
        } ?: run {
            boldOpenedOffset = matchResult.range.first - sizeReduction
        }
        sizeReduction += 2
        ""
    }
    return AnnotatedString(
        text = clearString,
        spanStyles = spanStyles,
    )
}
