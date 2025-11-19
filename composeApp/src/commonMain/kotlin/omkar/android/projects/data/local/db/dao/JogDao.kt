package omkar.android.projects.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import omkar.android.projects.data.local.db.entities.Joggable

@Dao
interface JogDao {
    @Insert
    suspend fun createNote(note: Joggable): Long

    @Update
    suspend fun updateNote(note: Joggable): Int

    @Delete
    suspend fun deleteNote(note: Joggable): Int

    @Query("SELECT * FROM jog_table WHERE id = :id LIMIT 1")
    suspend fun getNote(id: Long): Joggable?

    @Query("SELECT * FROM jog_table")
    fun getAllNotes(): Flow<List<Joggable>>
}
