package omkar.android.projects.domain.usecases

import omkar.android.projects.data.model.ExampleResponse
import omkar.android.projects.data.remote.util.ApiResponseWrapper
import omkar.android.projects.domain.repository.NotesJoggerRepository

class ExampleUseCase(
    private val exampleRepository: NotesJoggerRepository
) {
    suspend operator fun invoke() {
        return exampleRepository.createNotes()
    }
}