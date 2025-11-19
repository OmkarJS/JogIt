package omkar.android.projects.presentation.jogdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import co.touchlab.kermit.Logger
import jogit.composeapp.generated.resources.Res
import jogit.composeapp.generated.resources.jog_details
import omkar.android.projects.LocalAppColors
import omkar.android.projects.app.components.LargeSpacer
import omkar.android.projects.app.components.MediumSpacer
import omkar.android.projects.presentation.navigation.Screens
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

private const val TAG = "JogDetailPage"

@Composable
fun JogDetailPage(id: Long? = null) {
    val navigator = LocalNavigator.currentOrThrow
    val colors = LocalAppColors.current
    val jogDetailsViewModel: JogDetailsViewModel = koinInject()

    val passedNote by jogDetailsViewModel.noteItem.collectAsState()

    LaunchedEffect(Unit) {
        id?.let {
            jogDetailsViewModel.fetchNoteItemFromID(it)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = colors.black,
                modifier = Modifier.clickable {
                    navigator.pop()
                }
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                stringResource(Res.string.jog_details),
                modifier = Modifier
                    .weight(1f)
                    .clickable { navigator.pop() },
                textAlign = TextAlign.Center
            )
        }

        MediumSpacer()

        passedNote?.let {
            Logger.withTag(TAG).d("Passes Note = $it")
            OutlinedTextField(
                value = it.title,
                onValueChange = {
                    jogDetailsViewModel.updateNoteTitle(it)
                }
            )

            LargeSpacer()

            OutlinedTextField(
                value = it.content,
                onValueChange = {
                    jogDetailsViewModel.updateContent(it)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                modifier = Modifier.fillMaxWidth(0.96f),
                onClick = {
                    jogDetailsViewModel.updateNote(passedNote)
                    navigator.push(Screens.HomePage)
                },
                content = {
                    Text(
                        text = "Update",
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
            )
        }
    }
}