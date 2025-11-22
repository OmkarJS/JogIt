package omkar.android.projects.shared.password.data.local.model

data class PasswordDetailsState(
    val hash: String? = null,
    val salt: String? = null,
    val protected: Boolean = false
)
