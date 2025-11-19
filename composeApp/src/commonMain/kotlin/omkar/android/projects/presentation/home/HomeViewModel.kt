package omkar.android.projects.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import omkar.android.projects.data.local.db.entities.Joggable
import omkar.android.projects.domain.usecases.JogUseCases

class HomeViewModel(
    private val jogUseCases: JogUseCases
): ViewModel() {

    private val _notesList = MutableStateFlow<List<Joggable>>(emptyList())
    val notesList: StateFlow<List<Joggable>> = _notesList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            jogUseCases.getNotesListUseCase.invoke()
                .distinctUntilChanged()
                .collectLatest {
                _notesList.value = it
            }
        }
    }

    fun createNote(value: Joggable) {
        viewModelScope.launch(Dispatchers.IO) {
            jogUseCases.createNotesUseCase.invoke(value)
        }
    }

    fun deleteNote(value: Joggable) {
        viewModelScope.launch(Dispatchers.IO) {
            jogUseCases.deleteNoteUseCase.invoke(value)
        }
    }
}