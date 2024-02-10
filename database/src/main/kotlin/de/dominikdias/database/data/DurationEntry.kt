package de.dominikdias.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Duration(val duration: Long)


@Entity(tableName = "duration_table")
internal data class DurationEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "duration") val duration: Long
)

internal fun Duration.toDurationEntry() = DurationEntry(duration = duration)

internal fun DurationEntry.toDuration() = Duration(duration = duration)