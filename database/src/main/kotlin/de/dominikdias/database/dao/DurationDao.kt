package de.dominikdias.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import de.dominikdias.database.data.DurationEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface DurationDao {
    @Upsert
    suspend fun insert(duration: DurationEntry)

    @Query("SELECT * FROM duration_table")
    fun getAllDurations(): Flow<List<DurationEntry>>
}