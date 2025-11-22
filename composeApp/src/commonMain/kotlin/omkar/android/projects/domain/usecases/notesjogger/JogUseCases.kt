package omkar.android.projects.domain.usecases.notesjogger

import kotlinx.coroutines.flow.Flow
import omkar.android.projects.data.local.db.entities.Joggable
import omkar.android.projects.domain.repository.notesjogger.NotesJoggerRepository

data class JogUseCases(
    val createNotesUseCase: CreateNotesUseCase,
    val updateNotesUseCase: UpdateNotesUseCase,
    val getNotesListUseCase: GetNotesListUseCase,
    val getNoteFromIDUseCase: GetNoteFromIDUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase
)

class CreateNotesUseCase(
    private val notesJoggerRepository: NotesJoggerRepository
) {
    suspend operator fun invoke(note: Joggable): Long {
        return notesJoggerRepository.createNotes(note)
    }
}

class UpdateNotesUseCase(
    private val notesJoggerRepository: NotesJoggerRepository
) {
    suspend operator fun invoke(note: Joggable): Int {
        return notesJoggerRepository.updateNotes(note)
    }
}

class DeleteNoteUseCase(
    private val notesJoggerRepository: NotesJoggerRepository
) {
    suspend operator fun invoke(note: Joggable): Int {
        return notesJoggerRepository.deleteNote(note)
    }
}

class GetNoteFromIDUseCase(
    private val notesJoggerRepository: NotesJoggerRepository
) {
    suspend operator fun invoke(id: Long): Joggable? {
        return notesJoggerRepository.getNote(id)
    }
}

class GetNotesListUseCase(
    private val notesJoggerRepository: NotesJoggerRepository
) {
    operator fun invoke(): Flow<List<Joggable>> {
        return notesJoggerRepository.getAllNotes()
    }
}