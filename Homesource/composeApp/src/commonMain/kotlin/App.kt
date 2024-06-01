import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.logger.Level
import org.koin.dsl.module
import kotlin.random.Random

class Myfactory {
    val id = Random(0).nextInt()
}

val mod = module {
    factory { Myfactory() }
}

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(mod)
        printLogger(Level.DEBUG)
    }) {
        AppTheme {
            Text("Welcome to grade pal")
        }
    }
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    DynamicMaterialTheme(
        animate = true,
        seedColor = Color(0xff27ae60),
        style = PaletteStyle.FruitSalad,
        isExtendedFidelity = false,
        typography = Typography(),
        content = content
    )
}