package omkar.android.projects.di

import omkar.android.projects.data.local.db.AppDatabase
import omkar.android.projects.data.local.db.DataBaseFactory
import omkar.android.projects.data.local.db.createDatabase
import omkar.android.projects.data.local.db.dao.JogDao
import org.koin.dsl.module

val iosModule = module {
    single { DataBaseFactory() }
    single<AppDatabase> { createDatabase(get()) }
    single<JogDao> { get<AppDatabase>().jogDao() }
}