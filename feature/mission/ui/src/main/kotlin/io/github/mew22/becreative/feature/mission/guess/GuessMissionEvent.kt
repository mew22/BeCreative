package io.github.mew22.becreative.feature.mission.guess

sealed class GuessMissionEvent {
    data class UpdateGuess(val input: String) : GuessMissionEvent()
    data object Confirm : GuessMissionEvent()
}
