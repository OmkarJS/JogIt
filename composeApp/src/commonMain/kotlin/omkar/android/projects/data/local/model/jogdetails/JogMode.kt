package omkar.android.projects.data.local.model.jogdetails

import jogit.composeapp.generated.resources.Res
import jogit.composeapp.generated.resources.ic_save

sealed class JogMode(
    val iconRes: Any
) {
    object CREATE: JogMode(
        iconRes = Res.drawable.ic_save
    )

    data class UPDATE(
        val id: Long
    ): JogMode(
        iconRes = Res.drawable.ic_save
    )
}