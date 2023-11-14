package tv.rasucc.ov.time.data.storage

import android.content.Context
import tv.rasucc.ov.time.Constants
import tv.rasucc.ov.time.domain.storage.PointsStorage

class PointsStorageImpl(context: Context) : PointsStorage {

    private val sharedPrefStorage = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE)
    override fun savePoints(points: Int) {
        val currentPoints = sharedPrefStorage.getInt(Constants.POINTS_KEY, 0)
        if (points > currentPoints){
            sharedPrefStorage.edit().putInt(Constants.POINTS_KEY, points).apply()
        }
    }

    override fun getBestPoints(): Int {
        return sharedPrefStorage.getInt(Constants.POINTS_KEY, 0)
    }
}