package me.bitlinker.compose800.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
internal value class Vec2 private constructor(private val packedValue: Long) {
    val x: Int
        get() = packedValue.shr(32).toInt()

    val y: Int
        get() = packedValue.and(0xFFFFFFFF).toInt()

    operator fun plus(other: Vec2): Vec2 {
        return Vec2(x + other.x, y + other.y)
    }

    operator fun times(value: Int): Vec2 {
        return Vec2(x * value, y * value)
    }

    companion object {
        operator fun invoke(x: Int, y: Int): Vec2 {
            return Vec2(x.toLong().shl(32) or (y.toLong() and 0xFFFFFFFF))
        }
    }
}
