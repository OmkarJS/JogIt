package omkar.android.projects.data.local.db

import androidx.room.RoomDatabase

expect class DataBaseFactory {
    fun createDataBase(): RoomDatabase.Builder<AppDatabase>
}