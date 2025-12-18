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
import omkar.android.projects.shared.password.presentation.PasswordViewmodel
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
    val passwordViewModel = koinViewModel<PasswordViewmodel>()

    val passedNote by jogDetailsViewModel.noteItem.collectAsState()
    val jogMode by jogDetailsViewModel.jogMode.collectAsState()
    val passwordDetailsState by passwordViewModel.passcodeDetailState.collectAsState()
    val updateState by jogDetailsViewModel.updateState.collectAsState()
    val isBiometricsAvailable by passwordViewModel.isBiometricsAvailable.collectAsState()
    val biometricAuthenticationStatus by passwordViewModel.biometricAuthenticationStatus.collectAsState()

    // UI states
    var temporaryPassword by rememberSaveable { mutableStateOf("") }
    var temporaryPasscodeProtected by rememberSaveable { mutableStateOf(false) }
    var temporaryBiometricProtected by rememberSaveable { mutableStateOf(false) }
    var passwordBottomState by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Logger.withTag(TAG).d("LaunchedEffect: id: $id")
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

    LaunchedEffect(passwordDetailsState) {
        Logger.withTag(TAG).d("passwordDetailsState: $passwordDetailsState")
        passwordDetailsState?.let {
            jogDetailsViewModel.updateNotePasswordInfo(it)
            temporaryPasscodeProtected = it.protected
        }
    }

    LaunchedEffect(biometricAuthenticationStatus) {
        Logger.withTag(TAG).d("biometricState: $biometricAuthenticationStatus")
        jogDetailsViewModel.updateNoteBiometricStatus(biometricAuthenticationStatus == true)
        temporaryBiometricProtected = (biometricAuthenticationStatus == true)
    }

    LaunchedEffect(updateState) {
        updateState?.let {
            onBackPressed()
            jogDetailsViewModel.resetUpdateState()
        }
    }

    LaunchedEffect(passedNote) {
        passedNote?.let {
            temporaryPasscodeProtected = it.hasPasswordLock
            temporaryBiometricProtected = it.hasBiometricLock
        }
    }

    fun removePassCodeDetails() {
        jogDetailsViewModel.removePasswordLock()
        temporaryPasscodeProtected = false
        temporaryPassword = ""
        passwordBottomState = false
    }

    fun removeBiometricDetails() {
        jogDetailsViewModel.removeBiometricLock()
        temporaryBiometricProtected = false
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
                icon = passedNote?.let {
                    if (it.hasPasswordLock) Res.drawable.ic_locked else Res.drawable.ic_unlocked
                } ?: Res.drawable.ic_unlocked,
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
                        is JogMode.CREATE -> jogDetailsViewModel.createNote(passwordDetailsState, biometricAuthenticationStatus == true)
                        is JogMode.UPDATE -> jogDetailsViewModel.updateNote(passedNote)
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
                text = passedNote?.title ?: "",
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
                text = passedNote?.content ?: "",
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
            isPasscodeProtected = temporaryPasscodeProtected,
            isBiometricProtected = temporaryBiometricProtected,
            password = temporaryPassword,
            onPasswordChange = {
                temporaryPassword = it
            },
            onDismiss = {
                passwordBottomState = false
            },
            onLockWithPassword = { password ->
                passwordViewModel.setPassword(password)
                passwordBottomState = false
            },
            onLockWithFingerprint = {
                passwordViewModel.authenticateFingerprint()
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

