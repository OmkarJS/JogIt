package omkar.android.projects.shared.password

import co.touchlab.kermit.Logger
import omkar.android.projects.shared.password.app.util.sha256
import omkar.android.projects.shared.password.app.util.toHex
import omkar.android.projects.shared.password.domain.repository.BiometricAuthenticator
import omkar.android.projects.shared.password.domain.repository.BiometricResult
import kotlin.random.Random

private const val TAG = "PasswordManager"

class PasswordManager(private val biometricAuthenticator: BiometricAuthenticator) {
    fun hashPassword(password: String): String {
        val saltAddedString = password + generateSalt()
        val hash = sha256(saltAddedString)
        return hash.toHex()
    }

    fun hashPassword(password: String, salt: String): String {
        val saltAddedString = password + salt
        val hash = sha256(saltAddedString)
        return hash.toHex()
    }

    fun generateSalt(): String {
        val bytes = ByteArray(16)
        Random.nextBytes(bytes)
        return bytes.toHex()
    }

    fun verifyPassword(password: String, storedSalt: String, storedHash: String): Boolean {
        val newHash = hashPassword(password, storedSalt)
        return newHash == storedHash
    }

    // Fingerprint
    fun isBiometricAvailable(): Boolean {
        return biometricAuthenticator.isBiometricAvailable()
    }

    suspend fun authenticateFingerprint(): Boolean {
        Logger.withTag(TAG).d("onLockWithFingerprint: Is biometric available - ${biometricAuthenticator.isBiometricAvailable()}")
        val result = biometricAuthenticator.authenticate(
            title = "Unlock Notes",
            subtitle = "Unlock fingerprint to access notes."
        )

        when(result) {
            BiometricResult.Success -> {
                Logger.withTag(TAG).d("lockWithFingerprint: BiometricResult.Success")
                return true
            }

            is BiometricResult.Error -> {
                Logger.withTag(TAG).d("lockWithFingerprint: BiometricResult.Error - ${result.message}")
                return false
            }

            BiometricResult.Cancelled -> {
                Logger.withTag(TAG).d("lockWithFingerprint: BiometricResult.Cancelled")
                return false
            }

            BiometricResult.NotAvailable -> {
                Logger.withTag(TAG).d("lockWithFingerprint: BiometricResult.NotAvailable")
                return false
            }
        }
    }
}