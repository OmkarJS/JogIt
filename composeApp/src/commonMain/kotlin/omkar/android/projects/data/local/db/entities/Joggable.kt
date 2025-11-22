package omkar.android.projects.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jog_table")
data class Joggable(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val content: String,
    val protected: Boolean = false,
    val passwordHash: String? = null,
    val salt: String? = null
)