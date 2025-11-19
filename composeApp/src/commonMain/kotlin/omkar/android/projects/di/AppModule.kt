package omkar.android.projects.di


import omkar.android.projects.data.local.repository.NotesJoggerRepositoryImpl
import omkar.android.projects.data.remote.ExampleClient
import omkar.android.projects.data.remote.HttpClientEngine
import omkar.android.projects.domain.repository.NotesJoggerRepository
import omkar.android.projects.domain.usecases.CreateNotesUseCase
import omkar.android.projects.domain.usecases.DeleteNoteUseCase
import omkar.android.projects.domain.usecases.GetNoteFromIDUseCase
import omkar.android.projects.domain.usecases.GetNotesListUseCase
import omkar.android.projects.domain.usecases.JogUseCases
import omkar.android.projects.domain.usecases.UpdateNotesUseCase
import omkar.android.projects.presentation.home.HomeViewModel
import omkar.android.projects.presentation.jogdetails.JogDetailsViewModel
import org.koin.dsl.module

val commonModule = module {
    /**  Repository  */
    single<NotesJoggerRepository> { NotesJoggerRepositoryImpl(get()) }


    /**  Usecase  */
    // Joggable
    factory { CreateNotesUseCase(get()) }
    factory { UpdateNotesUseCase(get()) }
    factory { GetNotesListUseCase(get()) }
    factory { GetNoteFromIDUseCase(get()) }
    factory { DeleteNoteUseCase(get()) }
    factory {
        JogUseCases(
            createNotesUseCase = get(),
            updateNotesUseCase = get(),
            getNotesListUseCase = get(),
            getNoteFromIDUseCase = get(),
            deleteNoteUseCase = get()
        )
    }


    /**  Client  */
    val httpClient = HttpClientEngine().create()
    single { ExampleClient(httpClient = httpClient) }

    /**  Viewmodel  */
    single {
        // Returns single instance
        HomeViewModel(get())
    }

    factory {
        // Returns new viewmodel instance every time this is called. Add if needed.
        JogDetailsViewModel(
            updateNotesUseCase = get(),
            getNoteFromIDUseCase = get()
        )
    }
}