package omkar.android.projects

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import omkar.android.projects.presentation.navigation.MyAppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    AppTheme(darkTheme = isSystemInDarkTheme()) {
        KoinContext {
            MyAppNavigation()
        }
    }
}