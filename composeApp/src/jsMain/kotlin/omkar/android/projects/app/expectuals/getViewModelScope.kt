package omkar.android.projects.app.expectuals

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

actual fun getViewModelScope(): CoroutineScope = MainScope()