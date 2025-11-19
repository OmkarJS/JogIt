package omkar.android.projects.expectuals

import omkar.android.projects.di.iosModule
import org.koin.core.module.Module

actual fun getPlatformSpecificKoinModule(): Module {
    return iosModule
}