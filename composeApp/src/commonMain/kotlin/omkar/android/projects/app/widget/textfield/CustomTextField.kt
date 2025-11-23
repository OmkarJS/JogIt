package omkar.android.projects.app.widget.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import omkar.android.projects.LocalAppColors
import omkar.android.projects.app.components.DefaultSpacer
import omkar.android.projects.app.components.MediumText
import omkar.android.projects.app.components.TextConfig

@Composable
fun CustomTextField(
    text: String,
    onValueChange: (String) -> Unit,
    textConfig: TextConfig = TextConfig(),
    placeholder: @Composable (() -> Unit)? = null,
    passwordEnabled: Boolean = false,
    modifier: Modifier = Modifier,
    maxLength: Int = Int.MAX_VALUE,
    errorText: String? = null
) {
    val state = rememberStringTextFieldState(text, onValueChange, maxLength)
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier.then(textConfig.modifier),
            contentAlignment = Alignment.CenterStart
        ) {
            if (state.text.isEmpty() && placeholder != null) {
                placeholder()
            }

            Row(modifier = modifier) {
                if (passwordEnabled && !passwordVisible) {
                    BasicSecureTextField(
                        state = state,
                        textStyle = TextStyle(
                            fontSize = textConfig.fontSize,
                            fontWeight = textConfig.fontWeight,
                            color = textConfig.color ?: LocalAppColors.current.black,
                            textAlign = textConfig.textAlign
                        ),
                        cursorBrush = SolidColor(textConfig.color ?: LocalAppColors.current.black),
                    )
                } else {
                    BasicTextField(
                        state = state,
                        textStyle = TextStyle(
                            fontSize = textConfig.fontSize,
                            fontWeight = textConfig.fontWeight,
                            color = textConfig.color ?: LocalAppColors.current.black,
                            textAlign = textConfig.textAlign
                        ),
                        cursorBrush = SolidColor(textConfig.color ?: LocalAppColors.current.black)
                    )
                }

                if (passwordEnabled) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.Star
                        else
                            Icons.Default.Share,
                        contentDescription = null,
                        tint = LocalAppColors.current.grey,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { passwordVisible = !passwordVisible }
                    )
                }
            }
        }

        errorText?.let {
            DefaultSpacer()

            MediumText(
                text = it,
                TextConfig(color = LocalAppColors.current.error)
            )
        }
    }
}

@Composable
fun rememberStringTextFieldState(
    text: String,
    onValueChange: (String) -> Unit,
    maxLength: Int
): TextFieldState {
    val state = remember { TextFieldState(text) }

    LaunchedEffect(text) {
        val limitedText = text.take(maxLength)

        if (state.text.toString() != text) {
            state.edit {
                replace(0, state.text.length, limitedText)
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