package de.dominikdias.database.interfaces

import androidx.annotation.Keep
import de.dominikdias.database.data.Duration
import kotlinx.coroutines.flow.Flow

@Keep
interface IDurationRepository {
    suspend fun insertDuration(duration: Duration)
    suspend fun getAllDurations(): Flow<List<Duration>>
}