package omkar.android.projects

import androidx.compose.ui.window.ComposeUIViewController
import omkar.android.projects.app.utils.KoinUtils
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    KoinUtils.startKoin()

    return ComposeUIViewController { App() }
}
