package omkar.android.projects.data.local.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import co.touchlab.kermit.Logger
import omkar.android.projects.app.constants.Constants

actual class DataBaseFactory(private val context: Context) {
    actual fun createDataBase(): RoomDatabase.Builder<AppDatabase> {
        val applicationContext = context.applicationContext
        val dbFile = applicationContext.getDatabasePath(Constants.DataBaseConstants.DB_NAME)
        Logger.withTag("DataBaseFactory").d("DB PATH (ANDROID) = ${dbFile.absolutePath}")

        return Room.databaseBuilder<AppDatabase>(
            context = applicationContext,
            name = dbFile.absolutePath
        )
    }
}