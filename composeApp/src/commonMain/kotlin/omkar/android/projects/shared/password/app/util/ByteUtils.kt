package omkar.android.projects.shared.password.app.util

import omkar.android.projects.app.constants.Constants.PasswordConstants.hexChars

fun ByteArray.toHex(): String {
    return buildString(this.size * 2) {
        for (b in this@toHex) {
            val i = b.toInt() and 0xFF
            append(hexChars[i ushr 4])
            append(hexChars[i and 0x0F])
        }
    }
}
