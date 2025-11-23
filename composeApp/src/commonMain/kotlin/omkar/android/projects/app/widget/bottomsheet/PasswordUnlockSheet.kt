package omkar.android.projects.app.widget.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jogit.composeapp.generated.resources.Res
import jogit.composeapp.generated.resources.enter_password_to_unlock
import jogit.composeapp.generated.resources.unlock
import omkar.android.projects.LocalAppColors
import omkar.android.projects.app.components.ExtraLargeSpacer
import omkar.android.projects.app.components.LargeSpacer
import omkar.android.projects.app.components.LargeText
import omkar.android.projects.app.components.SemiLargeText
import omkar.android.projects.app.components.TextConfig
import omkar.android.projects.app.widget.button.RoundedButton
import omkar.android.projects.app.widget.textfield.CustomTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordUnlockSheet(
    onClickUnlock: (String) -> Unit,
    onDismiss: () -> Unit,
    errorMessage: String? = null
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var password by remember { mutableStateOf("") }

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
            LargeText(
                text = Res.string.enter_password_to_unlock,
                TextConfig(fontWeight = FontWeight.Bold)
            )

            ExtraLargeSpacer()

            CustomTextField(
                text = password,
                onValueChange = {
                    password = it
                },
                textConfig = TextConfig(fontSize = 22.sp),
                placeholder = {
                    SemiLargeText("⬤ ⬤ ⬤ ⬤", config = TextConfig(color = LocalAppColors.current.grey))
                },
                passwordEnabled = true,
                maxLength = 10,
                errorText = errorMessage
            )

            LargeSpacer()

            RoundedButton(
                buttonText = Res.string.unlock,
                onClick = {
                    onClickUnlock(password)
                },
                textConfig = TextConfig(fontSize = 14.sp, uppercase = true, color = LocalAppColors.current.white)
            )
        }
    }
}
