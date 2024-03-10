    package me.bitlinker.compose800.ui.utils

fun Int.hexToString(leadingZeroes: Int = 0): String {
    val str = toString(16)
    return buildString {
        append("0x")
        repeat((leadingZeroes - str.length).coerceAtLeast(0)) {
            append('0')
        }
        append(str)
    }
}
