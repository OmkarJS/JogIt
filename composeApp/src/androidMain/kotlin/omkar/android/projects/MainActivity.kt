package omkar.android.projects

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import omkar.android.projects.app.utils.KoinUtils
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpKoin()
        setContent {
            App()
        }
    }

    private fun setUpKoin() {
        if (GlobalContext.getOrNull() == null) {
            KoinUtils.startKoinProcess {
                androidContext(this@MainActivity)
                modules(module {
                    single<FragmentActivity> { this@MainActivity }
                })
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}