package omkar.android.projects.data.local.repository.notesjogger

import kotlinx.coroutines.flow.Flow
import omkar.android.projects.data.local.db.dao.JogDao
import omkar.android.projects.data.local.db.entities.Joggable
import omkar.android.projects.domain.repository.notesjogger.NotesJoggerRepository

class NotesJoggerRepositoryImpl(
    private val jogDao: JogDao
): NotesJoggerRepository {
    override suspend fun createNotes(note: Joggable): Long {
        return jogDao.createNote(note)
    }

    override suspend fun updateNotes(note: Joggable): Int {
        return jogDao.updateNote(note)
    }

    override suspend fun deleteNote(note: Joggable): Int {
        return jogDao.deleteNote(note)
    }

    override suspend fun getNote(id: Long): Joggable? {
        return jogDao.getNote(id)
    }

    override fun getAllNotes(): Flow<List<Joggable>> {
        return jogDao.getAllNotes()
    }
}