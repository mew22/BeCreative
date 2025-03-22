package io.github.mew22.becreative.feature.mission.guess

import androidx.compose.runtime.Immutable

@Immutable
data class GuessMissionState(
    val status: Status = Status.Idle,
    val imageUrl: String = "",
    val guess: String = "",
) {
    sealed class Status {
        data object Idle : Status()
        data object Loading : Status()
        data object Success : Status()
        data object Error : Status()
    }
}
