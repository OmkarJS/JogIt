package omkar.android.projects.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import omkar.android.projects.LocalAppColors
import omkar.android.projects.app.components.MediumSpacer
import omkar.android.projects.app.widget.icon.CustomIcon

@Composable
fun ProfilePage() {
    val navigator = LocalNavigator.currentOrThrow
    val colors = LocalAppColors.current

    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            CustomIcon(
                icon = Icons.Default.ArrowBack,
                onClick = { navigator.pop() }
            )
        }

        MediumSpacer()
    }
}