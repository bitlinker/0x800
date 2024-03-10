package me.bitlinker.compose800.model

import kotlin.math.pow

internal fun Int.pow2Of(): Int {
    return 2.0.pow(toDouble()).toInt()
}