package omkar.android.projects.app.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import jogit.composeapp.generated.resources.Res
import jogit.composeapp.generated.resources.hello_world
import omkar.android.projects.LocalAppColors
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 - Configuration class for text styling
 - Add new properties here and they'll be available to all text composable
 */
data class TextConfig(
    val textAlign: TextAlign = TextAlign.Start,
    val maxLines: Int = 1,
    val overflow: TextOverflow = TextOverflow.Ellipsis,
    val modifier: Modifier = Modifier,
    val fontWeight: FontWeight = FontWeight.Normal,
    val color: Color? = null,
    val fontSize: TextUnit = TextUnit.Unspecified,
    val uppercase: Boolean = false
)

/**
 - Base text composable that all other text variants use.
 - Add new styling properties to TextConfig and they'll automatically work everywhere.
 */
@Composable
private fun BaseText(
    text: Any,
    config: TextConfig = TextConfig()
) {
    val colorToBeUsed = config.color ?: LocalAppColors.current.black
    val textToBeUsed = when(text) {
        is String -> text
        is StringResource -> stringResource(text)
        else -> stringResource(Res.string.hello_world)
    }

    Text(
        text = if(config.uppercase) textToBeUsed.uppercase() else textToBeUsed,
        modifier = config.modifier,
        color = colorToBeUsed,
        fontSize = config.fontSize,
        fontWeight = config.fontWeight,
        textAlign = config.textAlign,
        maxLines = config.maxLines,
        overflow = config.overflow,
    )
}

@Composable
fun ExtraSmallText(
    text: Any,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    config = config.copy(fontSize = 10.sp)
)

@Composable
fun SmallText(
    text: Any,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    config = config.copy(fontSize = 12.sp)
)

@Composable
fun SemiMediumText(
    text: Any,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    config = config.copy(fontSize = 14.sp)
)

@Composable
fun MediumText(
    text: Any,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    config = config.copy(fontSize = 16.sp)
)

@Composable
fun SemiLargeText(
    text: Any,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    config = config.copy(fontSize = 18.sp)
)

@Composable
fun LargeText(
    text: Any,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    config = config.copy(fontSize = 20.sp)
)

@Composable
fun ExtraLargeText(
    text: Any,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    config = config.copy(fontSize = 22.sp)
)

@Composable
fun TitleSmallText(
    text: Any,
    config: TextConfig = TextConfig(fontWeight = FontWeight.Bold)
) = BaseText(
    text = text,
    config = config.copy(fontSize = 24.sp)
)

@Composable
fun TitleMediumText(
    text: Any,
    config: TextConfig = TextConfig(fontWeight = FontWeight.Bold)
) = BaseText(
    text = text,
    config = config.copy(fontSize = 26.sp)
)

@Composable
fun TitleLargeText(
    text: Any,
    config: TextConfig = TextConfig(fontWeight = FontWeight.Bold)
) = BaseText(
    text = text,
    config = config.copy(fontSize = 28.sp)
)

