package de.dominikdias.gymresttimer.application

import android.app.Application
import de.dominikdias.database.DurationDatabase
import de.dominikdias.database.repository.DurationRepository

class GymRestTimerApplication: Application() {
    val durationRepository by lazy { DurationRepository(DurationDatabase.getDatabase(this).durationDao()) }
}