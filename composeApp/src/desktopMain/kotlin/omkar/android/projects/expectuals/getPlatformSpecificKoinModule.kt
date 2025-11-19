package omkar.android.projects.expectuals

import omkar.android.projects.di.desktopModule
import org.koin.core.module.Module

actual fun getPlatformSpecificKoinModule(): Module {
    return desktopModule
}