package de.dominikdias.database.interfaces

import de.dominikdias.database.data.Duration
import kotlinx.coroutines.flow.Flow

interface IDurationRepository {
    suspend fun insertDuration(duration: Duration)
    suspend fun getAllDurations(): Flow<List<Duration>>
}