package omkar.android.projects.shared.password.expectuals

actual fun ByteArray.copyToIntArray(sourceOffset: Int, count: Int, target: IntArray) {
    for (i in 0 until count) {
        val base = sourceOffset + i * 4
        target[i] =
            ((this[base].toInt() and 0xFF) shl 24) or
                    ((this[base + 1].toInt() and 0xFF) shl 16) or
                    ((this[base + 2].toInt() and 0xFF) shl 8) or
                    (this[base + 3].toInt() and 0xFF)
    }
}