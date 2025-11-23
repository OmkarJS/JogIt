package omkar.android.projects.app.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SizedSpacer(size: Dp, horizontal: Boolean = false) {
    Spacer(
        modifier = if (horizontal) {
            Modifier
                .width(size)
        } else {
            Modifier
                .fillMaxWidth()
                .height(size)
        }
    )
}

@Composable
fun DefaultSpacer(horizontal: Boolean = false) =
    SizedSpacer(4.dp, horizontal)

@Composable
fun ExtraSmallSpacer(horizontal: Boolean = false) =
    SizedSpacer(6.dp, horizontal)

@Composable
fun SmallSpacer(horizontal: Boolean = false) =
    SizedSpacer(12.dp, horizontal)

@Composable
fun SemiMediumSpacer(horizontal: Boolean = false) =
    SizedSpacer(16.dp, horizontal)

@Composable
fun MediumSpacer(horizontal: Boolean = false) =
    SizedSpacer(18.dp, horizontal)

@Composable
fun SemiLargeSpacer(horizontal: Boolean = false) =
    SizedSpacer(20.dp, horizontal)

@Composable
fun LargeSpacer(horizontal: Boolean = false) =
    SizedSpacer(24.dp, horizontal)

@Composable
fun ExtraLargeSpacer(horizontal: Boolean = false) =
    SizedSpacer(30.dp, horizontal)