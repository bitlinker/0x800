package me.bitlinker.compose800

import App
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.essenty.statekeeper.stateKeeper

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initEdgeToEdge()
        super.onCreate(savedInstanceState)

        val stateKeeper = stateKeeper()

        setContent {
            App(stateKeeper = stateKeeper)
        }
    }

    private fun initEdgeToEdge() {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ),
        )
    }
}
