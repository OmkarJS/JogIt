package omkar.android.projects.presentation.jogdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import omkar.android.projects.data.local.db.entities.Joggable
import omkar.android.projects.data.local.model.jogdetails.JogMode
import omkar.android.projects.domain.usecases.CreateNotesUseCase
import omkar.android.projects.domain.usecases.GetNoteFromIDUseCase
import omkar.android.projects.domain.usecases.UpdateNotesUseCase

private const val TAG = "JogDetailsViewModel"

class JogDetailsViewModel(
    private val createNotesUseCase: CreateNotesUseCase,
    private val updateNotesUseCase: UpdateNotesUseCase,
    private val getNoteFromIDUseCase: GetNoteFromIDUseCase
): ViewModel() {

    private val _noteItem = MutableStateFlow<Joggable?>(null)
    val noteItem: StateFlow<Joggable?> = _noteItem.asStateFlow()

    private val _jogMode = MutableStateFlow<JogMode>(JogMode.CREATE)
    val jogMode = _jogMode.asStateFlow()

    private val _title = MutableStateFlow("")
    private val _content = MutableStateFlow("")

    fun fetchNoteItemFromID(value: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = getNoteFromIDUseCase.invoke(value)
            _noteItem.value = item
            Logger.withTag(TAG).d("fetchNoteItemFromID: noteItem: $item")
        }
    }

    fun updateNoteTitle(value: String) {
        _title.value = value
    }

    fun updateContent(value: String) {
        _content.value = value
    }

    fun updateJogMode(value: JogMode) {
        _jogMode.value = value
        Logger.withTag(TAG).d("updateJogMode: JogMode: $value")
    }

    fun createNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = Joggable(title = _title.value, content = _content.value)

            val result = createNotesUseCase.invoke(note)
            Logger.withTag(TAG).d("createNote: Status: ${ if(result != -1L) "Success" else "Failure"}")
        }
    }

    fun updateNote() {
        _noteItem.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val updatedNote = it.copy(title = _title.value, content = _content.value)

                val result = updateNotesUseCase.invoke(updatedNote)
                Logger.withTag(TAG).d("updateNote: Status: ${ if(result != 0) "Success" else "Failure"}")
            }
        }
    }
}