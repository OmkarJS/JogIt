package omkar.android.projects.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import jogit.composeapp.generated.resources.Res
import jogit.composeapp.generated.resources.compose_multiplatform
import omkar.android.projects.LocalAppColors
import omkar.android.projects.app.components.ExtraSmallSpacer
import omkar.android.projects.app.components.MediumText
import omkar.android.projects.app.components.SemiLargeText
import omkar.android.projects.app.components.TextConfig
import omkar.android.projects.app.widget.HomeRoofView
import omkar.android.projects.data.local.db.entities.Joggable
import omkar.android.projects.presentation.navigation.Screens
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun HomePage() {
    val navigator = LocalNavigator.currentOrThrow
    val colors = LocalAppColors.current
    val homeViewModel: HomeViewModel = koinInject()

    // Search
    var searchText by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    fun hideSearchSuggestion() {
        searchText = ""
        isSearching = false
    }

    // Joggable
    val notesList by homeViewModel.notesList.collectAsState()

    Scaffold(
        topBar = {
            HomeRoofView(
                isSearching = isSearching,
                searchText = searchText,
                onTextChange = {
                    searchText = it
                },
                onSearchClick = { searchQuery ->
                    hideSearchSuggestion()
                },
                onSearchBarClick = {
                    isSearching = true
                },
                onProfileClick = {
                    navigator.push(Screens.ProfilePage)
                },
                onCloseSearch = {
                    hideSearchSuggestion()
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notesList, key = { it.id }) { note ->
                    NoteItem(
                        note = note,
                        onClick = {
                            navigator.push(Screens.JogDetailScreen(note.id))
                        },
                        onDelete = {
                            homeViewModel.deleteNote(it)
                        }
                    )
                }
            }

            TextButton(
                modifier = Modifier.fillMaxWidth(0.96f),
                onClick = {
                    val note = Joggable(
                        title = "Jog it",
                        content = "Jog it fast you idiot."
                    )
                    homeViewModel.createNote(note)
                },
                content = {
                    Text(
                        text = "Create notes",
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
            )
        }
    }
}

@Composable
fun NoteItem(
    note: Joggable,
    onClick: () -> Unit,
    onDelete: (Joggable) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                SemiLargeText(
                    text = note.title,
                    TextConfig(
                        fontWeight = FontWeight.SemiBold
                    )
                )

                ExtraSmallSpacer()

                MediumText(
                    text = note.content,
                    TextConfig(
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(Res.drawable.compose_multiplatform),
                modifier = Modifier
                    .padding(6.dp)
                    .size(20.dp)
                    .clickable {
                        onDelete(note)
                    },
                contentDescription = "Delete"
            )
        }
    }
}

