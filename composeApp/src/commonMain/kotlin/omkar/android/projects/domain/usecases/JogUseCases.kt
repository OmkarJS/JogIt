package omkar.android.projects.domain.usecases

import kotlinx.coroutines.flow.Flow
import omkar.android.projects.data.local.db.entities.Joggable
import omkar.android.projects.domain.repository.NotesJoggerRepository

data class JogUseCases(
    val createNotesUseCase: CreateNotesUseCase,
    val updateNotesUseCase: UpdateNotesUseCase,
    val getNotesListUseCase: GetNotesListUseCase
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

class GetNotesListUseCase(
    private val notesJoggerRepository: NotesJoggerRepository
) {
    operator fun invoke(): Flow<List<Joggable>> {
        return notesJoggerRepository.getAllNotes()
    }
}