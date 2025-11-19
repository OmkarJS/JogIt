package omkar.android.projects.expectuals

import omkar.android.projects.di.androidModule
import org.koin.core.module.Module

actual fun getPlatformSpecificKoinModule(): Module {
    return androidModule
}