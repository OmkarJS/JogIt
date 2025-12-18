package omkar.android.projects.app.widget.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import jogit.composeapp.generated.resources.Res
import jogit.composeapp.generated.resources.ic_eye_close
import jogit.composeapp.generated.resources.ic_eye_open
import omkar.android.projects.LocalAppColors
import omkar.android.projects.app.components.DefaultSpacer
import omkar.android.projects.app.components.MediumText
import omkar.android.projects.app.components.TextConfig
import omkar.android.projects.app.widget.icon.CustomIcon

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
    val focusRequester = remember { FocusRequester() }
    val lineLimits = if(textConfig.maxLines == 1) {
        TextFieldLineLimits.SingleLine
    } else {
        TextFieldLineLimits.MultiLine(textConfig.maxLines)
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusRequester.requestFocus()
                }
               .padding(vertical = 4.dp)
               .padding(horizontal = if (passwordEnabled) 20.dp else 0.dp)
        ) {
            if (state.text.isEmpty() && placeholder != null) {
                placeholder()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val textFieldModifier = Modifier
                    .run {
                        if (passwordEnabled) weight(1f) else fillMaxWidth()
                    }
                    .focusRequester(focusRequester)

                val textStyle = TextStyle(
                    fontSize = textConfig.fontSize,
                    fontWeight = textConfig.fontWeight,
                    color = textConfig.color ?: LocalAppColors.current.black,
                    textAlign = textConfig.textAlign
                )

                if (passwordEnabled && !passwordVisible) {
                    BasicSecureTextField(
                        state = state,
                        textStyle = textStyle,
                        modifier = textFieldModifier,
                        cursorBrush = SolidColor(textConfig.color ?: LocalAppColors.current.black)
                    )
                } else {
                    BasicTextField(
                        state = state,
                        textStyle = textStyle,
                        modifier = textFieldModifier,
                        cursorBrush = SolidColor(textConfig.color ?: LocalAppColors.current.black),
                        lineLimits = lineLimits
                    )
                }

                if (passwordEnabled) {
                    CustomIcon(
                        icon = if (passwordVisible) Res.drawable.ic_eye_close else Res.drawable.ic_eye_open,
                        iconColor = LocalAppColors.current.grey,
                        modifier = Modifier
                            .padding(start = 8.dp)
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