package omkar.android.projects.shared.password.presentation

import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import omkar.android.projects.data.local.db.entities.Joggable
import omkar.android.projects.shared.password.data.local.model.PasswordDetailsState
import omkar.android.projects.shared.password.domain.usecases.PasswordUseCases

private const val TAG = "PasswordViewmodel"

class PasswordViewmodel(
    private val passwordUseCases: PasswordUseCases
): ViewModel() {
    private val _passwordDetailState = MutableStateFlow<PasswordDetailsState?>(null)
    val passwordDetailsState = _passwordDetailState.asStateFlow()

    private val _validationState = MutableStateFlow<Boolean?>(null)
    val validationState = _validationState.asStateFlow()

    fun setPassword(password: String) {
        val salt = generateSalt()
        val hash = hashPassword(password, salt)
        _passwordDetailState.value = PasswordDetailsState(
            salt = salt,
            hash = hash,
            protected = true
        )
        Logger.withTag(TAG).d("setPassword: salt: $salt, Hash: $hash")
    }

    fun hashPassword(password: String, storedSalt: String): String {
        return passwordUseCases.hashPasswordWithSaltUseCase.invoke(password, storedSalt)
    }

    fun generateSalt(): String {
        return passwordUseCases.generateSaltUseCase.invoke()
    }

    fun verifyPassword(password: String, storedHash: String?, storedSalt: String?) {
        if(storedHash != null && storedSalt != null) {
            _validationState.value = passwordUseCases.verifyPasswordUseCase.invoke(password, storedHash = storedHash, storedSalt = storedSalt)
        }
    }

    fun isProtected(joggable: Joggable): Boolean {
        return joggable.protected && joggable.passwordHash != null && joggable.salt != null
    }

    fun resetValidation() {
        _validationState.value = null
    }
}