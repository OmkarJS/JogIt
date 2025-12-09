package omkar.android.projects.di

import omkar.android.projects.data.local.repository.notesjogger.NotesJoggerRepositoryImpl
import omkar.android.projects.shared.password.data.local.repository.PasswordRepositoryImpl
import omkar.android.projects.data.remote.ExampleClient
import omkar.android.projects.data.remote.HttpClientEngine
import omkar.android.projects.domain.repository.notesjogger.NotesJoggerRepository
import omkar.android.projects.shared.password.domain.repository.PasswordRepository
import omkar.android.projects.domain.usecases.notesjogger.CreateNotesUseCase
import omkar.android.projects.domain.usecases.notesjogger.DeleteNoteUseCase
import omkar.android.projects.domain.usecases.notesjogger.GetNoteFromIDUseCase
import omkar.android.projects.domain.usecases.notesjogger.GetNotesListUseCase
import omkar.android.projects.domain.usecases.notesjogger.JogUseCases
import omkar.android.projects.domain.usecases.notesjogger.UpdateNotesUseCase
import omkar.android.projects.shared.password.domain.usecases.GenerateSaltUseCase
import omkar.android.projects.shared.password.domain.usecases.HashPasswordWithSaltUseCase
import omkar.android.projects.shared.password.domain.usecases.HashPasswordWithoutSaltUseCase
import omkar.android.projects.shared.password.domain.usecases.PasswordUseCases
import omkar.android.projects.shared.password.domain.usecases.VerifyPasswordUseCase
import omkar.android.projects.presentation.home.HomeViewModel
import omkar.android.projects.presentation.jogdetails.JogDetailsViewModel
import omkar.android.projects.shared.password.PasswordManager
import omkar.android.projects.shared.password.presentation.PasswordViewmodel
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel

val commonModule = module {
    /**  Managers  */
    single<PasswordManager> { PasswordManager() }

    /**  Repository  */
    // Notes jogger
    single<NotesJoggerRepository> { NotesJoggerRepositoryImpl(get()) }
    // Password
    single<PasswordRepository> { PasswordRepositoryImpl(get()) }

    /**  Usecase  */
    //  Notes jogger
    single { CreateNotesUseCase(get()) }
    single { UpdateNotesUseCase(get()) }
    single { GetNotesListUseCase(get()) }
    single { GetNoteFromIDUseCase(get()) }
    single { DeleteNoteUseCase(get()) }
    single {
        JogUseCases(
            createNotesUseCase = get(),
            updateNotesUseCase = get(),
            getNotesListUseCase = get(),
            getNoteFromIDUseCase = get(),
            deleteNoteUseCase = get()
        )
    }

    // Password
    single { HashPasswordWithoutSaltUseCase(get()) }
    single { HashPasswordWithSaltUseCase(get()) }
    single { GenerateSaltUseCase(get()) }
    single { VerifyPasswordUseCase(get()) }
    single {
        PasswordUseCases(
            hashPasswordWithoutSaltUseCase = get(),
            hashPasswordWithSaltUseCase = get(),
            generateSaltUseCase = get(),
            verifyPasswordUseCase = get()
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

    viewModel {
        JogDetailsViewModel(
            createNotesUseCase = get(),
            updateNotesUseCase = get(),
            getNoteFromIDUseCase = get()
        )
    }

    viewModel {
        PasswordViewmodel(
            passwordUseCases = get(),
            biometricAuthenticator = get()
        )
    }
}