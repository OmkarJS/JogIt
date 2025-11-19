package omkar.android.projects.expectuals

import java.awt.Toolkit

actual fun getScreenHeight(): Float {
    return Toolkit.getDefaultToolkit().screenSize.height.toFloat()
}

actual fun getScreenWidth(): Float {
    return Toolkit.getDefaultToolkit().screenSize.width.toFloat()
}