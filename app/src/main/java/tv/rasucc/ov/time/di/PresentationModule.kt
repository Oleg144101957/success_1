package tv.rasucc.ov.time.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import tv.rasucc.ov.time.domain.storage.PointsStorage
import tv.rasucc.ov.time.presentation.viewmodel.GameViewModel


@Module
@InstallIn(ActivityComponent::class)
class PresentationModule {
    @Provides
    fun provideGameViewModel(pointsStorage: PointsStorage) : GameViewModel{
        return GameViewModel(pointsStorage)
    }

}