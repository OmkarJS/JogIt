package omkar.android.projects.shared.password.biometrics

import omkar.android.projects.shared.password.domain.repository.BiometricAuthenticator
import omkar.android.projects.shared.password.domain.repository.BiometricResult

class IosBiometricAuth(): BiometricAuthenticator {
    override fun isBiometricAvailable(): Boolean {
        return false
    }

    override suspend fun authenticate(
        title: String,
        subtitle: String?,
        description: String?
    ): BiometricResult {
        TODO("Not yet implemented")
    }
}