package omkar.android.projects.shared.password.domain.repository

interface PasswordRepository {
    fun hashPassword(password: String): String
    fun hashPassword(password: String, salt: String): String
    fun generateSalt(): String
    fun verifyPassword(password: String, storedSalt: String, storedHash: String): Boolean
}