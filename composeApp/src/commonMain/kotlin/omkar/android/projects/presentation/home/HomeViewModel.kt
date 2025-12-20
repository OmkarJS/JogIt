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
import omkar.android.projects.domain.usecases.notesjogger.JogUseCases
import omkar.android.projects.shared.password.domain.usecases.ValidateBiometricUseCase
import omkar.android.projects.shared.password.domain.usecases.VerifyPasswordUseCase

class HomeViewModel(
    private val jogUseCases: JogUseCases,
    private val verifyPasswordUseCase: VerifyPasswordUseCase,
    private val validateBiometricUseCase: ValidateBiometricUseCase
): ViewModel() {

    private val _notesList = MutableStateFlow<List<Joggable>>(emptyList())
    val notesList: StateFlow<List<Joggable>> = _notesList.asStateFlow()

    // Security validation
    private val _passcodeValidationState = MutableStateFlow<Boolean?>(null)
    val passcodeValidationState = _passcodeValidationState.asStateFlow()

    private val _biometricAuthenticationStatus = MutableStateFlow<Boolean?>(null)
    val biometricAuthenticationStatus = _biometricAuthenticationStatus.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            jogUseCases.getNotesListUseCase.invoke()
                .distinctUntilChanged()
                .collectLatest {
                _notesList.value = it
            }
        }
    }

    fun resetPasswordValidation() {
        _passcodeValidationState.value = null
    }

    fun resetBiometricValidation() {
        _biometricAuthenticationStatus.value = null
    }

    fun deleteNote(value: Joggable) {
        viewModelScope.launch(Dispatchers.IO) {
            jogUseCases.deleteNoteUseCase.invoke(value)
        }
    }

    fun isProtected(joggable: Joggable): Boolean {
        return joggable.hasPasswordLock && joggable.passwordHash != null && joggable.salt != null ||
                joggable.hasBiometricLock
    }

    fun verifyPassword(password: String, storedHash: String?, storedSalt: String?) {
        if(storedHash != null && storedSalt != null) {
            _passcodeValidationState.value = verifyPasswordUseCase.invoke(password, storedHash = storedHash, storedSalt = storedSalt)
        }
    }

    fun validateBiometric() {
        viewModelScope.launch {
            _biometricAuthenticationStatus.value = validateBiometricUseCase.invoke()
        }
    }

}