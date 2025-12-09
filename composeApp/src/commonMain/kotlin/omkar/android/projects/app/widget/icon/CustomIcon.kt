package omkar.android.projects.app.widget.icon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import omkar.android.projects.LocalAppColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun CustomIcon(
    icon: Any,
    iconSize: Dp = 24.dp,
    iconColor: Color = LocalAppColors.current.black,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var modifier = modifier.size(iconSize)

    if (onClick != null) {
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            onClick()
        }
    }

    when(icon) {
        is ImageVector -> {
            Icon(
                imageVector = icon,
                contentDescription = "CustomIcon",
                tint = iconColor,
                modifier = modifier
            )
        }

        is DrawableResource -> {
            Icon(
                painter = painterResource(icon),
                contentDescription = "CustomIcon",
                tint = iconColor,
                modifier = modifier
            )
        }

        is Painter -> {
            Icon(
                painter = icon,
                contentDescription = "CustomIcon",
                tint = iconColor,
                modifier = modifier
            )
        }

        is ImageBitmap -> {
            Icon(
                bitmap = icon,
                contentDescription = "CustomIcon",
                tint = iconColor,
                modifier = modifier
            )
        }
    }
}