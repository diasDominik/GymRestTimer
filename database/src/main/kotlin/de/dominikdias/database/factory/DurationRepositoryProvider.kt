package de.dominikdias.database.factory

import android.content.Context
import androidx.annotation.Keep
import de.dominikdias.database.DurationDatabase
import de.dominikdias.database.interfaces.IDurationRepository
import de.dominikdias.database.repository.DurationRepositoryImpl

@Keep
object DurationRepositoryProvider {
    fun provideDatabaseRepository(context: Context): IDurationRepository {
        return DurationRepositoryImpl(DurationDatabase.getDatabase(context = context).durationDao())
    }
}