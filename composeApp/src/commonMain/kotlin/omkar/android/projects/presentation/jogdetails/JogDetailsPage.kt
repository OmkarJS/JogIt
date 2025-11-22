package omkar.android.projects.presentation.jogdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import jogit.composeapp.generated.resources.Res
import jogit.composeapp.generated.resources.content_label
import jogit.composeapp.generated.resources.title_label
import omkar.android.projects.LocalAppColors
import omkar.android.projects.app.components.MediumSpacer
import omkar.android.projects.app.components.SemiLargeText
import omkar.android.projects.app.components.SemiMediumSpacer
import omkar.android.projects.app.components.SmallSpacer
import omkar.android.projects.app.components.TextConfig
import omkar.android.projects.app.components.TitleMediumText
import omkar.android.projects.app.widget.icon.CustomIcon
import omkar.android.projects.app.widget.textfield.CustomTextField
import omkar.android.projects.data.local.model.jogdetails.JogMode
import omkar.android.projects.presentation.navigation.Screens
import org.koin.compose.koinInject

private const val TAG = "JogDetailPage"

@Composable
fun JogDetailPage(id: Long? = null) {
    val navigator = LocalNavigator.currentOrThrow
    val colors = LocalAppColors.current
    val jogDetailsViewModel: JogDetailsViewModel = koinInject()

    val passedNote by jogDetailsViewModel.noteItem.collectAsState()
    val jogMode by jogDetailsViewModel.jogMode.collectAsState()

    LaunchedEffect(Unit) {
        when(id) {
            is Long -> jogDetailsViewModel.updateJogMode(JogMode.UPDATE(id))
            else -> jogDetailsViewModel.updateJogMode(JogMode.CREATE)
        }
    }

    LaunchedEffect(jogMode) {
        when(jogMode) {
            is JogMode.CREATE -> {}
            is JogMode.UPDATE -> jogDetailsViewModel.fetchNoteItemFromID((jogMode as JogMode.UPDATE).id)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when (jogMode) {
                        is JogMode.CREATE -> jogDetailsViewModel.createNote()
                        is JogMode.UPDATE -> jogDetailsViewModel.updateNote()
                    }
                    navigator.push(Screens.HomePage)
                }
            ) {
                CustomIcon(icon = jogMode.iconRes)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 10.dp)
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

            CustomTextField(
                text = passedNote?.title ?: "",
                onValueChange = { jogDetailsViewModel.updateNoteTitle(it) },
                textConfig = TextConfig(
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                ),
                placeholder = {
                    TitleMediumText(
                        text = Res.string.title_label,
                        TextConfig(color = colors.grey, fontWeight = FontWeight.Bold)
                    )
                }
            )

            SemiMediumSpacer()

            CustomTextField(
                text = passedNote?.content ?: "",
                onValueChange = { jogDetailsViewModel.updateContent(it) },
                textConfig = TextConfig(
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    maxLines = Int.MAX_VALUE
                ),
                placeholder = {
                    SemiLargeText(
                        text = Res.string.content_label,
                        TextConfig(color = colors.grey)
                    )
                }
            )

            SmallSpacer()
        }
    }
}