package omkar.android.projects.domain.repository.notesjogger

import kotlinx.coroutines.flow.Flow
import omkar.android.projects.data.local.db.entities.Joggable

interface NotesJoggerRepository {
    suspend fun createNotes(note: Joggable): Long
    suspend fun updateNotes(note: Joggable): Int
    suspend fun deleteNote(note: Joggable): Int
    suspend fun getNote(id: Long): Joggable?
    fun getAllNotes(): Flow<List<Joggable>>
}