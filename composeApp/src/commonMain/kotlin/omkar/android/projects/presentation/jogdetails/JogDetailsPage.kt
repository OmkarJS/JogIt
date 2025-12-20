package omkar.android.projects.presentation.jogdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import jogit.composeapp.generated.resources.Res
import jogit.composeapp.generated.resources.content_label
import jogit.composeapp.generated.resources.ic_locked
import jogit.composeapp.generated.resources.ic_unlocked
import jogit.composeapp.generated.resources.title_label
import omkar.android.projects.LocalAppColors
import omkar.android.projects.app.components.SemiLargeText
import omkar.android.projects.app.components.SemiMediumSpacer
import omkar.android.projects.app.components.SmallSpacer
import omkar.android.projects.app.components.TextConfig
import omkar.android.projects.app.components.TitleMediumText
import omkar.android.projects.app.widget.bottomsheet.PasswordBottomSheet
import omkar.android.projects.app.widget.icon.CustomIcon
import omkar.android.projects.app.widget.textfield.CustomTextField
import omkar.android.projects.data.local.model.jogdetails.JogMode
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

private const val TAG = "JogDetailPage"

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun JogDetailPage(
    id: Long? = null,
    onBackPressed: () -> Unit
) {
    val colors = LocalAppColors.current
    val jogDetailsViewModel = koinViewModel<JogDetailsViewModel>()

    // Prerequisites
    val isBiometricsAvailable by jogDetailsViewModel.isBiometricsAvailable.collectAsState()

    // Data
    val jogMode by jogDetailsViewModel.jogMode.collectAsState()
    val title by jogDetailsViewModel.title.collectAsState()
    val content by jogDetailsViewModel.content.collectAsState()
    val passcodeState by jogDetailsViewModel.passcodeState.collectAsState()
    val biometricState by jogDetailsViewModel.biometricState.collectAsState()

    // Authentication
    val biometricAuthenticationStatus by jogDetailsViewModel.biometricAuthenticationStatus.collectAsState()

    // Process
    val noteUpdateState by jogDetailsViewModel.updateState.collectAsState()

    // UI states
    var temporaryPassword by rememberSaveable { mutableStateOf("") }
    var passwordBottomState by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Logger.withTag(TAG).d("Launch: id: $id")
        when(id) {
            is Long -> {
                jogDetailsViewModel.updateJogMode(JogMode.UPDATE(id))
                jogDetailsViewModel.fetchNoteItemFromID(id)
            }
            else -> jogDetailsViewModel.updateJogMode(JogMode.CREATE)
        }
    }

    LaunchedEffect(biometricAuthenticationStatus) {
        Logger.withTag(TAG).d("Launch - biometricState: $biometricAuthenticationStatus")
        jogDetailsViewModel.updateNoteBiometricStatus(biometricAuthenticationStatus == true)
    }

    LaunchedEffect(noteUpdateState) {
        noteUpdateState?.let {
            onBackPressed()
            jogDetailsViewModel.resetUpdateState()
        }
    }

    fun removePassCodeDetails() {
        jogDetailsViewModel.removePasswordLock()
        temporaryPassword = ""
        passwordBottomState = false
    }

    fun removeBiometricDetails() {
        jogDetailsViewModel.removeBiometricLock()
        passwordBottomState = false
    }

    @Composable
    fun DetailTopBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomIcon(
                icon = Icons.Default.ArrowBack,
                onClick = {
                    onBackPressed()
                }
            )

            CustomIcon(
                icon = if (passcodeState || biometricState) Res.drawable.ic_locked
                else Res.drawable.ic_unlocked,
                onClick = {
                    passwordBottomState = true
                }
            )
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when (jogMode) {
                        is JogMode.CREATE -> jogDetailsViewModel.createNote()
                        is JogMode.UPDATE -> jogDetailsViewModel.updateNote((jogMode as JogMode.UPDATE).id)
                    }
                },
                backgroundColor = colors.primary
            ) {
                CustomIcon(icon = jogMode.iconRes, iconColor = Color.White)
            }
        },
        topBar = {
            DetailTopBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            // Title
            CustomTextField(
                text = title,
                onValueChange = { jogDetailsViewModel.updateNoteTitle(it) },
                textConfig = TextConfig(
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    maxLines = 1
                ),
                placeholder = {
                    TitleMediumText(
                        text = Res.string.title_label,
                        TextConfig(color = colors.grey, fontWeight = FontWeight.Bold)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            SemiMediumSpacer()

            // Content
            CustomTextField(
                text = content,
                onValueChange = { jogDetailsViewModel.updateContent(it) },
                textConfig = TextConfig(
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    maxLines = Int.MAX_VALUE,

                ),
                placeholder = {
                    SemiLargeText(
                        text = Res.string.content_label,
                        TextConfig(color = colors.grey)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            SmallSpacer()
        }

        PasswordBottomSheet(
            showBottomSheet = passwordBottomState,
            isPasscodeProtected = passcodeState,
            isBiometricProtected = biometricState,
            password = temporaryPassword,
            onPasswordChange = {
                temporaryPassword = it
            },
            onDismiss = {
                passwordBottomState = false
            },
            onLockWithPassword = { password ->
                jogDetailsViewModel.setPassword(password)
                passwordBottomState = false
            },
            onLockWithFingerprint = {
                jogDetailsViewModel.validateBiometric()
            },
            removePassCode = {
                removePassCodeDetails()
            },
            removeBiometricLock = {
                removeBiometricDetails()
            },
            isFingerprintAvailable = isBiometricsAvailable
        )
    }
}

