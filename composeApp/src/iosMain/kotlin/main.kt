import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import com.russhwolf.settings.NSUserDefaultsSettings
import me.bitlinker.compose800.di.AppComponentDependencies
import me.bitlinker.compose800.ui.App
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

@Suppress("FunctionName", "unused")
fun MainViewController(): UIViewController {

    val appComponentDependencies by lazy {
        AppComponentDependencies(
            settings = NSUserDefaultsSettings.Factory().create(),
            urlOpener = ::openUrl,
            stateKeeper = StateKeeperDispatcher(),
        )
    }

    return ComposeUIViewController(
        configure = {
            enforceStrictPlistSanityCheck = false
        },
        content = {
            App(appComponentDependencies)
        }
    )
}

private fun openUrl(url: String) {
    val nsUrl = NSURL.URLWithString(url) ?: return
    UIApplication.sharedApplication.openURL(nsUrl)
}
