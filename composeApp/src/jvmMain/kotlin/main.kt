import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import game0x800.composeapp.generated.resources.Res
import game0x800.composeapp.generated.resources.ic_launcher
import game0x800.composeapp.generated.resources.text_app_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.awt.Dimension

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
    Window(
        title = stringResource(Res.string.text_app_title),
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        icon = painterResource(Res.drawable.ic_launcher),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        App()
    }
}