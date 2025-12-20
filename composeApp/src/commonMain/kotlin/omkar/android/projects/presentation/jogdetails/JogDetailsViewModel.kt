package omkar.android.projects.presentation.jogdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import omkar.android.projects.data.local.db.entities.Joggable
import omkar.android.projects.data.local.model.jogdetails.JogMode
import omkar.android.projects.domain.usecases.notesjogger.CreateNotesUseCase
import omkar.android.projects.domain.usecases.notesjogger.GetNoteFromIDUseCase
import omkar.android.projects.domain.usecases.notesjogger.UpdateNotesUseCase
import omkar.android.projects.shared.password.domain.usecases.PasswordUseCases

private const val TAG = "JogDetailsViewModel"

class JogDetailsViewModel(
    private val createNotesUseCase: CreateNotesUseCase,
    private val updateNotesUseCase: UpdateNotesUseCase,
    private val getNoteFromIDUseCase: GetNoteFromIDUseCase,
    private val passwordUseCases: PasswordUseCases,
): ViewModel() {
    private val _jogMode = MutableStateFlow<JogMode>(JogMode.CREATE)
    val jogMode = _jogMode.asStateFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private var localHash: String? = null
    private var localSalt: String? = null

    private val _passcodeState = MutableStateFlow(false)
    val passcodeState = _passcodeState.asStateFlow()

    private val _biometricState = MutableStateFlow(false)
    val biometricState = _biometricState.asStateFlow()

    // Authentication check for locking the note with fingerprint
    private val _biometricAuthenticationStatus = MutableStateFlow<Boolean?>(null)
    val biometricAuthenticationStatus = _biometricAuthenticationStatus.asStateFlow()

    private val _isBiometricsAvailable = MutableStateFlow<Boolean>(false)
    val isBiometricsAvailable = _isBiometricsAvailable.asStateFlow()

    private val _updateState = MutableStateFlow<Boolean?>(null)
    val updateState = _updateState.asStateFlow()

    init {
        _isBiometricsAvailable.value = passwordUseCases.biometricAvailableUseCase.invoke()
    }

    fun fetchNoteItemFromID(value: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = getNoteFromIDUseCase.invoke(value)
            updateNoteTitle(item?.title ?: "")
            updateContent(item?.content ?: "")
            updateNotePasswordInfo(
                item?.salt,
                item?.passwordHash,
                item?.hasPasswordLock == true
            )
            updateNoteBiometricStatus(item?.hasBiometricLock == true)

            Logger.withTag(TAG).d("fetchNoteItemFromID: noteItem: $item")
        }
    }

    fun updateNoteTitle(value: String) {
        _title.value = value
    }

    fun updateContent(value: String) {
        _content.value = value
    }

    fun setPassword(password: String) {
        val salt = passwordUseCases.generateSaltUseCase.invoke()
        val hash = passwordUseCases.hashPasswordWithSaltUseCase.invoke(password, salt)

        _passcodeState.value = true
        localSalt = salt
        localHash = hash

        Logger.withTag(TAG).d("setPassword: salt: $salt, Hash: $hash")
    }

    private fun updateNotePasswordInfo(
        savedSalt: String? = null,
        savedHash: String? = null,
        protected: Boolean
    ) {
        _passcodeState.value = protected
        localSalt = savedSalt
        localHash = savedHash

        Logger.withTag(TAG).d("updateNotePasswordInfo: " +
                "passwordHash: $savedSalt" +
                "salt: $savedHash" +
                "hasPasswordLock: $protected"
        )
    }

    fun updateNoteBiometricStatus(value: Boolean) {
        _biometricState.value = value
        Logger.withTag(TAG).d("updateNoteBiometricStatus: value: $value, noteItem: ${_biometricState.value}")
    }

    fun removePasswordLock() {
        _passcodeState.value = false
        localSalt = null
        localHash = null

        Logger.withTag(TAG).d("removePasswordInfo: " +
                "passwordHash: ${localHash}" +
                "salt: ${localSalt}" +
                "hasPasswordLock: ${_passcodeState.value}"
        )
    }

    fun removeBiometricLock() {
        _biometricState.value = false
        Logger.withTag(TAG).d("removePasswordInfo: noteItem: ${_biometricState.value}")
    }

    fun updateJogMode(value: JogMode) {
        _jogMode.value = value
        Logger.withTag(TAG).d("updateJogMode: JogMode: $value")
    }

    fun resetUpdateState() {
        _updateState.value = null
    }

    fun validateBiometric() {
        viewModelScope.launch {
            _biometricAuthenticationStatus.value = passwordUseCases.validateBiometricUseCase.invoke()
        }
    }

    fun createNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = Joggable(
                title = _title.value,
                content = _content.value,
                passwordHash = localHash,
                salt = localSalt,
                hasPasswordLock = _passcodeState.value,
                hasBiometricLock = _biometricState.value
            )

            val result = createNotesUseCase.invoke(note)
            _updateState.value = result != -1L
            Logger.withTag(TAG)
                .d("createNote: Status: ${if (result != -1L) "Success" else "Failure"}")
        }
    }

    fun updateNote(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = Joggable(
                id = id,
                title = _title.value,
                content = _content.value,
                passwordHash = localHash,
                salt = localSalt,
                hasPasswordLock = _passcodeState.value == true,
                hasBiometricLock = _biometricState.value
            )

            val result = updateNotesUseCase.invoke(note)
            _updateState.value = result != 0
            Logger.withTag(TAG)
                .d("updateNote: Status: ${if (result != 0) "Success" else "Failure"}")
        }
    }

    override fun onCleared() {
        Logger.withTag(TAG).d("onCleared")
        super.onCleared()
    }
}