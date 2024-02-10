package de.dominikdias.database.factory

import android.content.Context
import de.dominikdias.database.DurationDatabase
import de.dominikdias.database.interfaces.DurationRepositoryImpl
import de.dominikdias.database.repository.DurationRepository

object DurationRepositoryProvider {
    fun provideDatabaseRepository(context: Context): DurationRepositoryImpl {
        return DurationRepository(DurationDatabase.getDatabase(context = context).durationDao())
    }
}