package omkar.android.projects.app.widget.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jogit.composeapp.generated.resources.Res
import jogit.composeapp.generated.resources.lock_with_fingerprint
import jogit.composeapp.generated.resources.lock_with_passcode
import jogit.composeapp.generated.resources.remove_passcode
import omkar.android.projects.LocalAppColors
import omkar.android.projects.app.components.ExtraLargeSpacer
import omkar.android.projects.app.components.MediumSpacer
import omkar.android.projects.app.components.SemiLargeText
import omkar.android.projects.app.components.SmallSpacer
import omkar.android.projects.app.components.TextConfig
import omkar.android.projects.app.widget.button.RoundedButton
import omkar.android.projects.app.widget.textfield.CustomTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordBottomSheet(
    showBottomSheet: Boolean,
    isProtected: Boolean = false,
    password: String,
    onPasswordChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onLockWithPassword: (String) -> Unit,
    onLockWithFingerprint: () -> Unit,
    removePassCode: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if(showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismiss,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MediumSpacer()

                CustomTextField(
                    text = password,
                    onValueChange = onPasswordChange,
                    textConfig = TextConfig(fontSize = 22.sp),
                    placeholder = {
                        SemiLargeText("⬤ ⬤ ⬤ ⬤", config = TextConfig(color = LocalAppColors.current.grey))
                    },
                    passwordEnabled = true,
                    maxLength = 10
                )

                ExtraLargeSpacer()

                RoundedButton(
                    buttonText = if(!isProtected) Res.string.lock_with_passcode else Res.string.remove_passcode,
                    onClick = {
                        if(!isProtected) onLockWithPassword(password)
                        else removePassCode()
                    },
                    textConfig = TextConfig(fontSize = 14.sp, uppercase = true, color = LocalAppColors.current.white)
                )

                SmallSpacer()

                RoundedButton(
                    buttonText = Res.string.lock_with_fingerprint,
                    onClick = {
                        onLockWithFingerprint()
                    },
                    buttonColor = LocalAppColors.current.white,
                    textConfig = TextConfig(fontSize = 14.sp, uppercase = true)
                )
            }
        }
    }
}