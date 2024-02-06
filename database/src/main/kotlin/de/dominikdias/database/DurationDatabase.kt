package de.dominikdias.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.dominikdias.database.callback.PrepopulateRoomCallback
import de.dominikdias.database.dao.DurationDao
import de.dominikdias.database.data.DurationEntry

@Database(entities = [DurationEntry::class], version = 1)
abstract class DurationDatabase : RoomDatabase() {

    abstract fun durationDao(): DurationDao

    companion object {
        @Volatile
        private var instance: DurationDatabase? = null

        fun getDatabase(context: Context): DurationDatabase {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        DurationDatabase::class.java,
                        "duration_database",
                    )
                        .addCallback(PrepopulateRoomCallback(context = context))
                        .build()
                    this.instance = instance
                }
                instance!!
            }
        }
    }
}