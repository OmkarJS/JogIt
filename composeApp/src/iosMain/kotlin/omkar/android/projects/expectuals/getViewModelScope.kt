package omkar.android.projects.expectuals

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

actual fun getViewModelScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)