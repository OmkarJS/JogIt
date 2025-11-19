package omkar.android.projects.presentation.jogdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import omkar.android.projects.data.local.db.entities.Joggable
import omkar.android.projects.domain.usecases.GetNoteFromIDUseCase
import omkar.android.projects.domain.usecases.UpdateNotesUseCase

class JogDetailsViewModel(
    private val updateNotesUseCase: UpdateNotesUseCase,
    private val getNoteFromIDUseCase: GetNoteFromIDUseCase
): ViewModel() {

    private val _noteItem = MutableStateFlow<Joggable?>(null)
    val noteItem: StateFlow<Joggable?> = _noteItem.asStateFlow()

    fun fetchNoteItemFromID(value: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = getNoteFromIDUseCase.invoke(value)
            _noteItem.value = item
        }
    }

    fun updateNoteTitle(value: String) {
        val current = _noteItem.value ?: return
        _noteItem.value = current.copy(title = value)
    }

    fun updateContent(value: String) {
        val current = _noteItem.value ?: return
        _noteItem.value = current.copy(content = value)
    }

    fun updateNote(value: Joggable?) {
        value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                updateNotesUseCase.invoke(it)
            }
        }
    }
}