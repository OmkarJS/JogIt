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
import omkar.android.projects.LocalAppColors
import omkar.android.projects.app.components.TextConfig

@Composable
fun RoundedButton(
    buttonText: String,
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
        Text(
            text = if(textConfig.uppercase) buttonText.uppercase() else buttonText,
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