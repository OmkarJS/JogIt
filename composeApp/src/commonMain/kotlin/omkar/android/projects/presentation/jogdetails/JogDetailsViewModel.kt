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
import omkar.android.projects.domain.usecases.notesjogger.CreateNotesUseCase
import omkar.android.projects.domain.usecases.notesjogger.GetNoteFromIDUseCase
import omkar.android.projects.domain.usecases.notesjogger.UpdateNotesUseCase
import omkar.android.projects.shared.password.data.local.model.PasswordDetailsState

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

    private val _updateState = MutableStateFlow<Boolean?>(null)
    val updateState = _updateState.asStateFlow()

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

    fun updateNotePasswordInfo(passwordDetailsState: PasswordDetailsState) {
        _noteItem.value = _noteItem.value?.copy(
            passwordHash = passwordDetailsState.hash,
            salt = passwordDetailsState.salt,
            protected = passwordDetailsState.protected
        )
        Logger.withTag(TAG).d("updateNotePasswordInfo: noteItem: ${_noteItem.value}")
    }

    fun removePasswordInfo() {
        _noteItem.value = _noteItem.value?.copy(
            passwordHash = null,
            salt = null,
            protected = false
        )
        Logger.withTag(TAG).d("removePasswordInfo: noteItem: ${_noteItem.value}")
    }

    fun updateJogMode(value: JogMode) {
        _jogMode.value = value
        Logger.withTag(TAG).d("updateJogMode: JogMode: $value")
    }

    fun resetUpdateState() {
        _updateState.value = null
    }

    fun createNote(passwordDetailsState: PasswordDetailsState? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = Joggable(
                title = _title.value,
                content = _content.value,
                passwordHash = passwordDetailsState?.hash,
                salt = passwordDetailsState?.salt,
                protected = passwordDetailsState?.protected == true
            )

            val result = createNotesUseCase.invoke(note)
            _updateState.value = result != -1L
            Logger.withTag(TAG).d("createNote: Status: ${ if(result != -1L) "Success" else "Failure"}, note: $note")
        }
    }

    fun updateNote(joggable: Joggable? = null) {
        joggable?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val result = updateNotesUseCase.invoke(it)
                _updateState.value = result != 0
                Logger.withTag(TAG).d("updateNote: Status: ${ if(result != 0) "Success" else "Failure"}, note: $it")
            }
        }
    }

    override fun onCleared() {
        Logger.withTag(TAG).d("onCleared")
        super.onCleared()
    }
}