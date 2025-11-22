package omkar.android.projects.app.utils

import cafe.adriel.voyager.navigator.Navigator
import omkar.android.projects.presentation.navigation.Screens

fun Navigator.navigateToNoteDetailsScreen(id: Long? = null) {
    this.push(Screens.JogDetailScreen(id))
}

fun Navigator.navigateToHomeScreen() {
    this.push(Screens.HomePage)
}

fun Navigator.navigateToProfileScreen() {
    this.push(Screens.ProfilePage)
}