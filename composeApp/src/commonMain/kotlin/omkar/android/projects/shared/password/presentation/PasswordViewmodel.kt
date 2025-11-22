package omkar.android.projects.shared.password.presentation

import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import omkar.android.projects.shared.password.data.local.model.PasswordDetailsState
import omkar.android.projects.shared.password.domain.usecases.PasswordUseCases

private const val TAG = "PasswordViewmodel"

class PasswordViewmodel(
    private val passwordUseCases: PasswordUseCases
): ViewModel() {
    private val _passwordDetailState = MutableStateFlow<PasswordDetailsState?>(null)
    val passwordDetailsState = _passwordDetailState.asStateFlow()

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

    fun verifyPassword(password: String, storedHash: String, storedSalt: String): Boolean {
        return passwordUseCases.verifyPasswordUseCase.invoke(password, storedHash = storedHash, storedSalt = storedSalt)
    }
}