import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.logger.Level

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(preAuthModule, stylesModule)
        printLogger(Level.DEBUG)
    }) {
        AppTheme {
            Surface {
                BottomSheetNavigator() {
                    Navigator(SignInScreen()) { navigator ->
                        SlideTransition(navigator)
                    }
                }
            }
        }
    }
}