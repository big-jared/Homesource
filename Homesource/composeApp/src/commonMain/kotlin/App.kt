import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
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
        MaterialTheme {
            var text by remember { mutableStateOf("Hello, World!") }
            val factory = koinInject<Myfactory>()
            MaterialTheme {
                Button(onClick = {
                    text = "Hello, Koin! ${factory.id}"
                }) {
                    Text(text)
                }
            }
        }
    }
}