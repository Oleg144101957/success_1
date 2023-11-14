package tv.rasucc.ov.time.domain.storage

import dagger.hilt.EntryPoints

interface PointsStorage {
    fun savePoints(points: Int)
    fun getBestPoints() : Int

}