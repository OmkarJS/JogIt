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
)

/**
 - Base text composable that all other text variants use.
 - Add new styling properties to TextConfig and they'll automatically work everywhere.
 */
@Composable
private fun BaseText(
    text: Any,
    fontSize: TextUnit,
    config: TextConfig = TextConfig()
) {
    val colorToBeUsed = config.color ?: LocalAppColors.current.black
    val textToBeUsed = when(text) {
        is String -> text
        is StringResource -> stringResource(text)
        else -> stringResource(Res.string.hello_world)
    }

    Text(
        text = textToBeUsed,
        modifier = config.modifier,
        color = colorToBeUsed,
        fontSize = fontSize,
        fontWeight = config.fontWeight,
        textAlign = config.textAlign,
        maxLines = config.maxLines,
        overflow = config.overflow
    )
}

@Composable
fun ExtraSmallText(
    text: String,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    fontSize = 10.sp,
    config = config
)

@Composable
fun SmallText(
    text: String,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    fontSize = 12.sp,
    config = config
)

@Composable
fun SemiMediumText(
    text: String,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    fontSize = 14.sp,
    config = config
)

@Composable
fun MediumText(
    text: String,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    fontSize = 16.sp,
    config = config
)

@Composable
fun SemiLargeText(
    text: String,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    fontSize = 18.sp,
    config = config
)

@Composable
fun LargeText(
    text: String,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    fontSize = 20.sp,
    config = config
)

@Composable
fun ExtraLargeText(
    text: String,
    config: TextConfig = TextConfig()
) = BaseText(
    text = text,
    fontSize = 22.sp,
    config = config
)

@Composable
fun TitleSmallText(
    text: String,
    config: TextConfig = TextConfig(fontWeight = FontWeight.Bold)
) = BaseText(
    text = text,
    fontSize = 24.sp,
    config = config
)

@Composable
fun TitleMediumText(
    text: String,
    config: TextConfig = TextConfig(fontWeight = FontWeight.Bold)
) = BaseText(
    text = text,
    fontSize = 26.sp,
    config = config
)

@Composable
fun TitleLargeText(
    text: String,
    config: TextConfig = TextConfig(fontWeight = FontWeight.Bold)
) = BaseText(
    text = text,
    fontSize = 28.sp,
    config = config
)

