package omkar.android.projects.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import omkar.android.projects.presentation.theme.LocalAppColors

@Composable
fun ProfilePage() {
    val navigator = LocalNavigator.currentOrThrow
    val colors = LocalAppColors.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Search Icon",
                tint = colors.black,
                modifier = Modifier.clickable {
                    navigator.pop()
                }
            )
        }

        Text(
            "Profile",
            modifier = Modifier
                .weight(1f)
                .clickable { navigator.pop() }
        )
    }
}