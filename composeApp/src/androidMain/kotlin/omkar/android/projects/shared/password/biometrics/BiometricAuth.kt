package omkar.android.projects.shared.password.biometrics

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import omkar.android.projects.shared.password.domain.repository.BiometricAuthenticator
import omkar.android.projects.shared.password.domain.repository.BiometricResult
import kotlin.coroutines.resume

class AndroidBiometricAuth(private val activity: FragmentActivity): BiometricAuthenticator {
    private val biometricManager = BiometricManager.from(activity)

    override fun isBiometricAvailable(): Boolean {
        return when (biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.BIOMETRIC_WEAK
        )) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }

    override suspend fun authenticate(
        title: String,
        subtitle: String?,
        description: String?
    ): BiometricResult = suspendCancellableCoroutine { continuation ->

        val executor = ContextCompat.getMainExecutor(activity)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult
            ) {
                super.onAuthenticationSucceeded(result)
                if (continuation.isActive) {
                    continuation.resume(BiometricResult.Success)
                }
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (continuation.isActive) {
                    when (errorCode) {
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON,
                        BiometricPrompt.ERROR_USER_CANCELED -> {
                            continuation.resume(BiometricResult.Cancelled)
                        }
                        BiometricPrompt.ERROR_NO_BIOMETRICS,
                        BiometricPrompt.ERROR_HW_NOT_PRESENT,
                        BiometricPrompt.ERROR_HW_UNAVAILABLE -> {
                            continuation.resume(BiometricResult.NotAvailable)
                        }
                        else -> {
                            continuation.resume(BiometricResult.Error(errString.toString()))
                        }
                    }
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // Don't resume here, let user retry
            }
        }

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .apply {
                subtitle?.let { setSubtitle(it) }
                description?.let { setDescription(it) }
            }
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.BIOMETRIC_WEAK
            )
            .build()

        val biometricPrompt = BiometricPrompt(activity, executor, callback)
        biometricPrompt.authenticate(promptInfo)

        continuation.invokeOnCancellation {
            biometricPrompt.cancelAuthentication()
        }
    }
}