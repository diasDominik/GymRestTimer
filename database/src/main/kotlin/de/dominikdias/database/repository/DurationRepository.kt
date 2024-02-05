package de.dominikdias.database.repository

import de.dominikdias.database.dao.DurationDao
import de.dominikdias.database.data.DurationEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class DurationRepository(private val durationDao: DurationDao) {
    suspend fun insertDuration(duration: DurationEntry) = withContext(Dispatchers.IO) {
        durationDao.insert(duration = duration)
    }

    suspend fun getAllDurations(): Flow<List<DurationEntry>> = withContext(Dispatchers.IO) {
        return@withContext durationDao.getAllDurations()
    }
}