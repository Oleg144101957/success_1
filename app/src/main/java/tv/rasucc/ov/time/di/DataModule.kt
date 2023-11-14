package tv.rasucc.ov.time.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import tv.rasucc.ov.time.data.storage.PointsStorageImpl
import tv.rasucc.ov.time.domain.storage.PointsStorage
@Module
@InstallIn(ActivityComponent::class)
class DataModule {
    @Provides
    fun providePointsForTheActivity(@ApplicationContext context: Context) : PointsStorage {
        return PointsStorageImpl(context = context)
    }
}