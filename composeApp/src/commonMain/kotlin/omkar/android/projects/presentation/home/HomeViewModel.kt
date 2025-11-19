package omkar.android.projects.presentation.home

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import omkar.android.projects.data.local.db.entities.Joggable
import omkar.android.projects.domain.usecases.CreateNotesUseCase
import omkar.android.projects.expectuals.getViewModelScope

class HomeViewModel(
    private val createNotesUseCase: CreateNotesUseCase
) {
    private val viewModelScope: CoroutineScope = getViewModelScope()

    private val _notesList = MutableStateFlow<List<Joggable>>(emptyList())
    val notesList: StateFlow<List<Joggable>> = _notesList.asStateFlow()

    init {

    }
}