package omkar.android.projects

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import omkar.android.projects.app.utils.KoinUtils
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        KoinUtils.startKoinProcess(
            config = {
                androidContext(this@MyApplication)
            }
        )
    }
}