package omkar.android.projects.data.local.db

import androidx.room.Room
import androidx.room.RoomDatabase
import co.touchlab.kermit.Logger
import kotlinx.cinterop.ExperimentalForeignApi
import omkar.android.projects.app.constants.Constants
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DataBaseFactory() {
    actual fun createDataBase(): RoomDatabase.Builder<AppDatabase> {
        val dbFilePath = documentDirectory() + "/${Constants.DataBaseConstants.DB_NAME}"
        Logger.withTag("DataBaseFactory").d("DB PATH (IOS) = $dbFilePath")
        return Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}