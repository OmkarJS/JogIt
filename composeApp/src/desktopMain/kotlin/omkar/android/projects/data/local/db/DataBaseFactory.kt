package omkar.android.projects.data.local.db

import androidx.room.Room
import androidx.room.RoomDatabase
import co.touchlab.kermit.Logger
import omkar.android.projects.app.constants.Constants
import java.io.File

actual class DataBaseFactory {
    actual fun createDataBase(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), Constants.DataBaseConstants.DB_NAME)
        Logger.withTag("DataBaseFactory").d("DB PATH (DESKTOP) = ${dbFile.absolutePath}")
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile.absolutePath,
        )
    }
}