package me.bitlinker.compose800.android

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.essenty.statekeeper.stateKeeper
import com.russhwolf.settings.SharedPreferencesSettings
import me.bitlinker.compose800.ui.App
import me.bitlinker.compose800.di.AppComponentDependencies
import androidx.core.net.toUri


class AppActivity : ComponentActivity() {

    private val appComponentDependencies by lazy {
        AppComponentDependencies(
            settings = SharedPreferencesSettings.Factory(application).create(),
            urlOpener = ::openUrl,
            stateKeeper = stateKeeper(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(deps = appComponentDependencies)
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

    private fun openUrl(url: String) {
        val openUrlIntent = Intent(Intent.ACTION_VIEW).also {
            it.data = url.toUri()
        }
        startActivity(openUrlIntent)
    }
}
