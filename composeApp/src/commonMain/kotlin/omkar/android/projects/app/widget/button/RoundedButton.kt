package omkar.android.projects.app.widget.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import jogit.composeapp.generated.resources.Res
import jogit.composeapp.generated.resources.hello_world
import omkar.android.projects.LocalAppColors
import omkar.android.projects.app.components.TextConfig
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RoundedButton(
    buttonText: Any,
    buttonColor: Color = LocalAppColors.current.primary,
    onClick: () -> Unit,
    buttonHeight: Dp = 45.dp,
    textConfig: TextConfig = TextConfig(),
    shape: Shape = RoundedCornerShape(30.dp)
) {
    OutlinedButton(
        onClick = {
            onClick()
        },
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(buttonHeight)
    ) {
        val textUsed = when(buttonText) {
            is String -> buttonText
            is StringResource -> stringResource(buttonText)
            else -> stringResource(Res.string.hello_world)
        }

        Text(
            text = if(textConfig.uppercase) textUsed.uppercase() else textUsed,
            maxLines = 1,
            style = TextStyle(
                fontSize = textConfig.fontSize,
                fontWeight = textConfig.fontWeight,
                color = textConfig.color ?: LocalAppColors.current.black,
                textAlign = textConfig.textAlign
            )
        )
    }
}