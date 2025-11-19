package omkar.android.projects.app.utils

import omkar.android.projects.expectuals.getPlatformSpecificKoinModule
import omkar.android.projects.di.commonModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

object KoinUtils {
    fun startKoinProcess(
        config: (KoinApplication.() -> Unit)? = null
    ) {
        startKoin {
            config?.invoke(this)
            modules(commonModule, getPlatformSpecificKoinModule())
        }
    }
}