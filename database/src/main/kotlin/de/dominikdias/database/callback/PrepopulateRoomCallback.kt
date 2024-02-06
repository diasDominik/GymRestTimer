package de.dominikdias.database.callback

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import de.dominikdias.database.DurationDatabase
import de.dominikdias.database.R
import de.dominikdias.database.data.DurationEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class PrepopulateRoomCallback(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            prePopulateDurations(context)
        }
    }

    private suspend fun prePopulateDurations(context: Context) {
        try {
            val durationDao = DurationDatabase.getDatabase(context = context).durationDao()

            val durationList = context.resources.openRawResource(R.raw.duration).bufferedReader()
                .use {
                    JSONArray(it.readText())
                }

            durationList.takeIf { it.length() > 0 }?.let { list ->
                for (index in 0 until list.length()) {
                    val durationObj = list.getJSONObject(index)
                    Log.e(TAG, "duration: ${durationObj.getLong("duration")}")
                    durationDao.insert(
                        DurationEntry(
                            id = durationObj.getInt("id"),
                            duration = durationObj.getLong("duration")
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.localizedMessage ?: "failed to pre-populate duration into database")
        }
    }

    private companion object {
        const val TAG = "PrePopulate"
    }
}