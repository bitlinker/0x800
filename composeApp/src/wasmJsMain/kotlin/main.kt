import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import com.russhwolf.settings.StorageSettings
import com.russhwolf.settings.observable.makeObservable
import kotlinx.browser.document
import kotlinx.browser.window
import me.bitlinker.compose800.di.AppComponentDependencies
import me.bitlinker.compose800.ui.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val appComponentDependencies by lazy {
        AppComponentDependencies(
            settings = StorageSettings().makeObservable(),
            urlOpener = ::openUrl,
            stateKeeper = StateKeeperDispatcher(),
        )
    }

    ComposeViewport(document.body!!) {
        App(appComponentDependencies)
    }
}

private fun openUrl(url: String) {
    window.open(url)
}
