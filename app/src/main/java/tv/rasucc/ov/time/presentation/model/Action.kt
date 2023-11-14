package tv.rasucc.ov.time.presentation.model

sealed class Action{
    data object IncreasePoints : Action()
    data object DecreaseHealth : Action()
    data object StartPlaying : Action()
    data object FinishGame : Action()
    data object StartIncreasingPower : Action()
    data object FinishIncreasingPower : Action()

}
