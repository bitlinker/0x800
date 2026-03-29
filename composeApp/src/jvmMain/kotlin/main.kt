import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import com.russhwolf.settings.PreferencesSettings
import me.bitlinker.compose800.composeapp.generated.resources.*
import io.github.aakira.napier.Napier
import me.bitlinker.compose800.ui.App
import me.bitlinker.compose800.di.AppComponentDependencies
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.awt.Desktop
import java.awt.Dimension
import java.net.URI

fun main() {

    val appComponentDependencies by lazy {
        AppComponentDependencies(
            settings = PreferencesSettings.Factory().create(),
            urlOpener = ::openUrl,
            stateKeeper = StateKeeperDispatcher(),
        )
    }

    application {
        Window(
            title = stringResource(Res.string.game_text_app_title),
            state = rememberWindowState(width = 800.dp, height = 600.dp),
            icon = painterResource(Res.drawable.ic_launcher),
            onCloseRequest = ::exitApplication,
        ) {
            window.minimumSize = Dimension(350, 600)
            App(appComponentDependencies)
        }
    }
}

private fun openUrl(url: String) {
    try {
        val desktop = Desktop.getDesktop()
        desktop.browse(URI.create(url))
    } catch (e: Exception) {
        Napier.e { "Failed to open URL: $url with error: ${e.message}" }
    }
}