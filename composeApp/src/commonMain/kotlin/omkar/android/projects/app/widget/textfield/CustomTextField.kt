package omkar.android.projects.app.widget.textfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import omkar.android.projects.LocalAppColors
import omkar.android.projects.app.components.TextConfig

@Composable
fun CustomTextField(
    text: String,
    onValueChange: (String) -> Unit,
    textConfig: TextConfig = TextConfig(),
    placeholder: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val state = rememberStringTextFieldState(text, onValueChange)

    BasicTextField(
        state = state,
        textStyle = TextStyle(
            fontSize = textConfig.fontSize,
            fontWeight = textConfig.fontWeight,
            color = textConfig.color ?: LocalAppColors.current.black,
            textAlign = textConfig.textAlign
        ),
        cursorBrush = SolidColor(textConfig.color ?: LocalAppColors.current.black),
        modifier = modifier.then(textConfig.modifier),
        decorator = { inner ->
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                if (state.text.isEmpty() && placeholder != null) {
                    placeholder()
                }
                inner()
            }
        }
    )
}

@Composable
fun rememberStringTextFieldState(
    text: String,
    onValueChange: (String) -> Unit
): TextFieldState {
    val state = remember { TextFieldState(text) }

    LaunchedEffect(text) {
        if (state.text.toString() != text) {
            state.edit {
                replace(0, state.text.length, text)
            }
        }
    }

    LaunchedEffect(state.text) {
        val newValue = state.text.toString()
        if (newValue != text) {
            onValueChange(newValue)
        }
    }

    return state
}