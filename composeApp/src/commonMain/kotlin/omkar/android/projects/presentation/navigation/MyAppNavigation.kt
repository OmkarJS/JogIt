package omkar.android.projects.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.touchlab.kermit.Logger
import omkar.android.projects.LocalAppColors
import omkar.android.projects.app.constants.Constants.Routes
import omkar.android.projects.app.utils.navigateToNoteDetailsScreen
import omkar.android.projects.app.utils.navigateToProfileScreen
import omkar.android.projects.presentation.home.HomePage
import omkar.android.projects.presentation.jogdetails.JogDetailPage
import omkar.android.projects.presentation.profile.ProfilePage

private const val TAG = "MyAppNavigation"

@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        composable(Routes.HOME) {
            HomePage(
                onProfileClicked = {
                    navController.navigateToProfileScreen()
                },
                onNoteClicked = {
                    Logger.withTag(TAG).d("LaunchedEffect: id: $it")
                    navController.navigateToNoteDetailsScreen(it)
                },
                onCreateNoteClicked = {
                    navController.navigateToNoteDetailsScreen()
                }
            )
        }

        composable(Routes.PROFILE) {
            ProfilePage(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.JOG_DETAILS_NO_ARG) {
            JogDetailPage(id = null, onBackPressed = {
                navController.popBackStack()
            })
        }

        composable(Routes.JOG_DETAILS_WITH_ARG) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            JogDetailPage(id = id, onBackPressed = {
                navController.popBackStack()
            })
        }
    }
}