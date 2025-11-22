package omkar.android.projects.shared.password.data.local.repository

import omkar.android.projects.shared.password.domain.repository.PasswordRepository
import omkar.android.projects.shared.password.PasswordManager

class PasswordRepositoryImpl(
    private val passwordManager: PasswordManager
): PasswordRepository {
    override fun hashPassword(password: String): String {
        return passwordManager.hashPassword(password)
    }

    override fun hashPassword(password: String, salt: String): String {
        return passwordManager.hashPassword(password, salt)
    }

    override fun generateSalt(): String {
        return passwordManager.generateSalt()
    }

    override fun verifyPassword(
        password: String,
        storedSalt: String,
        storedHash: String
    ): Boolean {
        return passwordManager.verifyPassword(password, storedSalt, storedHash)
    }
}