package omkar.android.projects.presentation.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import omkar.android.projects.presentation.home.HomePage
import omkar.android.projects.presentation.jogdetails.JogDetailPage
import omkar.android.projects.presentation.profile.ProfilePage

inline fun <reified T> T.toJson(): String = Json.encodeToString(this)

sealed class Screens() : Screen {

    object HomePage : Screens() {
        @Composable
        override fun Content() {
            HomePage()
        }
    }

    object ProfilePage : Screens() {
        @Composable
        override fun Content() {
            ProfilePage()
        }
    }

    data class JogDetailScreen(val id: Long? = null) : Screens() {
        @Composable
        override fun Content() {
            JogDetailPage(id)
        }
    }

    // Add more
}