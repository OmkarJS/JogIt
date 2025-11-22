package omkar.android.projects.shared.password

import omkar.android.projects.shared.password.app.util.sha256
import omkar.android.projects.shared.password.app.util.toHex
import kotlin.random.Random

class PasswordManager() {

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
}