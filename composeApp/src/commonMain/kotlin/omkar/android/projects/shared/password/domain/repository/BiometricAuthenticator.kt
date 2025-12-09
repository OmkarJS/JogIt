package omkar.android.projects.shared.password.domain.repository

interface BiometricAuthenticator {
    fun isBiometricAvailable(): Boolean
    suspend fun authenticate(
        title: String,
        subtitle: String? = null,
        description: String? = null
    ): BiometricResult
}

sealed class BiometricResult {
    object Success : BiometricResult()
    data class Error(val message: String) : BiometricResult()
    object Cancelled : BiometricResult()
    object NotAvailable : BiometricResult()
}