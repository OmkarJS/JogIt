package omkar.android.projects.shared.password.domain.usecases

import omkar.android.projects.shared.password.domain.repository.PasswordRepository

data class PasswordUseCases(
    val hashPasswordWithoutSaltUseCase: HashPasswordWithoutSaltUseCase,
    val hashPasswordWithSaltUseCase: HashPasswordWithSaltUseCase,
    val generateSaltUseCase: GenerateSaltUseCase,
    val verifyPasswordUseCase: VerifyPasswordUseCase,
    val biometricAvailableUseCase: BiometricAvailableUseCase,
    val validateBiometricUseCase: ValidateBiometricUseCase
)

// Passcode
class HashPasswordWithoutSaltUseCase(private val passwordRepository: PasswordRepository) {
    operator fun invoke(password: String): String {
        return passwordRepository.hashPassword(password)
    }
}

class HashPasswordWithSaltUseCase(private val passwordRepository: PasswordRepository) {
    operator fun invoke(password: String, salt: String): String {
        return passwordRepository.hashPassword(password, salt)
    }
}

class GenerateSaltUseCase(private val passwordRepository: PasswordRepository) {
    operator fun invoke(): String {
        return passwordRepository.generateSalt()
    }
}

class VerifyPasswordUseCase(private val passwordRepository: PasswordRepository) {
    operator fun invoke(password: String, storedSalt: String, storedHash: String): Boolean {
        return passwordRepository.verifyPassword(password, storedSalt, storedHash)
    }
}

// Biometric
class BiometricAvailableUseCase(private val passwordRepository: PasswordRepository) {
    operator fun invoke(): Boolean {
        return passwordRepository.isBiometricAvailable()
    }
}

class ValidateBiometricUseCase(private val passwordRepository: PasswordRepository) {
    suspend operator fun invoke(): Boolean {
        return passwordRepository.authenticateFingerprint()
    }
}