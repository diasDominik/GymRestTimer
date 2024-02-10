package de.dominikdias.database.repository

import de.dominikdias.database.dao.DurationDao
import de.dominikdias.database.data.Duration
import de.dominikdias.database.data.toDuration
import de.dominikdias.database.data.toDurationEntry
import de.dominikdias.database.interfaces.IDurationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


internal class DurationRepositoryImpl(private val durationDao: DurationDao): IDurationRepository {
    override suspend fun insertDuration(duration: Duration) = withContext(Dispatchers.IO) {
        durationDao.insert(duration = duration.toDurationEntry())
    }

    override suspend fun getAllDurations(): Flow<List<Duration>> = withContext(Dispatchers.IO) {
        return@withContext durationDao.getAllDurations().map { durationEntryList ->
            durationEntryList.map { durationEntry ->
                durationEntry.toDuration()
            }
        }
    }
}