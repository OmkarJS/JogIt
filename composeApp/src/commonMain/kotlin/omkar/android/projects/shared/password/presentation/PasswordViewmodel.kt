package omkar.android.projects.shared.password.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import omkar.android.projects.data.local.db.entities.Joggable
import omkar.android.projects.shared.password.data.local.model.PasswordDetailsState
import omkar.android.projects.shared.password.domain.repository.BiometricAuthenticator
import omkar.android.projects.shared.password.domain.repository.BiometricResult
import omkar.android.projects.shared.password.domain.usecases.PasswordUseCases

private const val TAG = "PasswordViewmodel"

class PasswordViewmodel(
    private val passwordUseCases: PasswordUseCases,
    private val biometricAuthenticator: BiometricAuthenticator
): ViewModel() {

    // Passcode
    private val _passcodeDetailState = MutableStateFlow<PasswordDetailsState?>(null)
    val passcodeDetailState = _passcodeDetailState.asStateFlow()

    private val _passcodeValidationState = MutableStateFlow<Boolean?>(null)
    val passcodeValidationState = _passcodeValidationState.asStateFlow()

    // Biometric
    private val _isBiometricsAvailable = MutableStateFlow<Boolean>(false)
    val isBiometricsAvailable = _isBiometricsAvailable.asStateFlow()

    private val _biometricAuthenticationStatus = MutableStateFlow<Boolean?>(null)
    val biometricAuthenticationStatus = _biometricAuthenticationStatus.asStateFlow()

    init {
        viewModelScope.launch {
            _isBiometricsAvailable.value = biometricAuthenticator.isBiometricAvailable()
        }
    }

    fun setPassword(password: String) {
        val salt = generateSalt()
        val hash = hashPassword(password, salt)
        _passcodeDetailState.value = PasswordDetailsState(
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
            _passcodeValidationState.value = passwordUseCases.verifyPasswordUseCase.invoke(password, storedHash = storedHash, storedSalt = storedSalt)
        }
    }

    fun isProtected(joggable: Joggable): Boolean {
        return joggable.hasPasswordLock && joggable.passwordHash != null && joggable.salt != null ||
                joggable.hasBiometricLock
    }

    fun resetPasswordValidation() {
        _passcodeValidationState.value = null
    }

    fun resetBiometricValidation() {
        _biometricAuthenticationStatus.value = null
    }

    fun authenticateFingerprint() {
        viewModelScope.launch {
            Logger.withTag(TAG).d("onLockWithFingerprint: Is biometric available - ${biometricAuthenticator.isBiometricAvailable()}")
            val result = biometricAuthenticator.authenticate(
                title = "Unlock Notes",
                subtitle = "Unlock fingerprint to access notes."
            )

            when(result) {
                BiometricResult.Success -> {
                    Logger.withTag(TAG).d("lockWithFingerprint: BiometricResult.Success")
                    _biometricAuthenticationStatus.value = true
                }

                is BiometricResult.Error -> {
                    Logger.withTag(TAG).d("lockWithFingerprint: BiometricResult.Error - ${result.message}")
                }

                BiometricResult.Cancelled -> {
                    Logger.withTag(TAG).d("lockWithFingerprint: BiometricResult.Cancelled")
                }

                BiometricResult.NotAvailable -> {
                    Logger.withTag(TAG).d("lockWithFingerprint: BiometricResult.NotAvailable")
                }
            }
        }
    }
}