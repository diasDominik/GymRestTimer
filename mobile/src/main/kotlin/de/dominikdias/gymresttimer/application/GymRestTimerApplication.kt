package de.dominikdias.gymresttimer.application

import android.app.Application
import de.dominikdias.database.factory.DurationRepositoryProvider

class GymRestTimerApplication: Application() {
    val durationRepository by lazy { DurationRepositoryProvider.provideDatabaseRepository(this) }
}