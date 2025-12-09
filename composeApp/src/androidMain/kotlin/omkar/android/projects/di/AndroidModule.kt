package omkar.android.projects.di

import omkar.android.projects.data.local.db.AppDatabase
import omkar.android.projects.data.local.db.DataBaseFactory
import omkar.android.projects.data.local.db.createDatabase
import omkar.android.projects.data.local.db.dao.JogDao
import omkar.android.projects.shared.password.biometrics.AndroidBiometricAuth
import omkar.android.projects.shared.password.domain.repository.BiometricAuthenticator
import org.koin.dsl.module

val androidModule = module {
    single { DataBaseFactory(get()) }
    single<AppDatabase> { createDatabase(get()) }
    single<JogDao> { get<AppDatabase>().jogDao() }

    single<BiometricAuthenticator> {
        AndroidBiometricAuth(get())
    }
}