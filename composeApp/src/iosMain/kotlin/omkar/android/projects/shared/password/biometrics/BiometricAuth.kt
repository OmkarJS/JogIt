package omkar.android.projects.shared.password.biometrics

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import omkar.android.projects.shared.password.domain.repository.BiometricAuthenticator
import omkar.android.projects.shared.password.domain.repository.BiometricResult
import platform.Foundation.NSError
import platform.LocalAuthentication.LAContext
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthenticationWithBiometrics
import kotlin.coroutines.resume

@OptIn(ExperimentalForeignApi::class)
class IosBiometricAuth : BiometricAuthenticator {

    companion object {
        private const val LA_ERROR_USER_CANCEL = -2L
        private const val LA_ERROR_SYSTEM_CANCEL = -4L
        private const val LA_ERROR_BIOMETRY_NOT_ENROLLED = -6L
        private const val LA_ERROR_BIOMETRY_NOT_AVAILABLE = -7L
    }

    override fun isBiometricAvailable(): Boolean {
        val context = LAContext()

        return memScoped {
            val error = alloc<ObjCObjectVar<NSError?>>()
            val canEvaluate = context.canEvaluatePolicy(
                policy = LAPolicyDeviceOwnerAuthenticationWithBiometrics,
                error = error.ptr
            )
            canEvaluate
        }
    }

    override suspend fun authenticate(
        title: String,
        subtitle: String?,
        description: String?
    ): BiometricResult = suspendCancellableCoroutine { continuation ->

        val context = LAContext()
        val reason = listOfNotNull(title, subtitle, description).joinToString("\n")

        context.localizedCancelTitle = "Cancel"

        val timeoutJob = CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            if (continuation.isActive) {
                context.invalidate()
                continuation.resume(BiometricResult.Error("Timeout: No activity detected"))
            }
        }

        context.evaluatePolicy(
            policy = LAPolicyDeviceOwnerAuthenticationWithBiometrics,
            localizedReason = reason
        ) { success, error ->
            timeoutJob.cancel()

            if (continuation.isActive) {
                when {
                    success -> {
                        context.invalidate()
                        continuation.resume(BiometricResult.Success)
                    }
                    error != null -> {
                        val errorCode = error.code
                        context.invalidate()
                        when (errorCode) {
                            LA_ERROR_USER_CANCEL, LA_ERROR_SYSTEM_CANCEL -> continuation.resume(BiometricResult.Cancelled)
                            LA_ERROR_BIOMETRY_NOT_ENROLLED, LA_ERROR_BIOMETRY_NOT_AVAILABLE -> continuation.resume(BiometricResult.NotAvailable)
                            else -> continuation.resume(BiometricResult.Error(error.localizedDescription))
                        }
                    }
                    else -> continuation.resume(BiometricResult.Error("Unknown error"))
                }
            }
        }

        continuation.invokeOnCancellation {
            timeoutJob.cancel()
            context.invalidate()
        }
    }
}