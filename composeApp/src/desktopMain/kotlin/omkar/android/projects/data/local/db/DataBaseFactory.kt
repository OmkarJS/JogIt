package omkar.android.projects.data.local.db

import androidx.room.Room
import androidx.room.RoomDatabase
import co.touchlab.kermit.Logger
import omkar.android.projects.app.constants.Constants
import java.io.File

actual class DataBaseFactory {
    actual fun createDataBase(): RoomDatabase.Builder<AppDatabase> {
        val dbName = Constants.DataBaseConstants.DB_NAME

        Logger.withTag("DataBaseFactory").d("DB NAME (DESKTOP) = $dbName")

        return Room.databaseBuilder<AppDatabase>(
            name = dbName,
        )
    }
}