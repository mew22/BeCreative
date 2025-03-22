package io.github.mew22.becreative.feature.mission.guess

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import io.github.mew22.becreative.core.monitoring.LogService
import io.github.mew22.becreative.core.ui.MviViewModel
import io.github.mew22.becreative.feature.mission.MissionRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GuessMissionViewModel(
    logService: LogService,
    val missionRepository: MissionRepository,
    savedStateHandle: SavedStateHandle
) : MviViewModel<GuessMissionEvent, GuessMissionState>(
    GuessMissionState(
        imageUrl = checkNotNull(savedStateHandle[IMAGE_URL])
    ),
    logService
) {

    private val missionId: String = checkNotNull(savedStateHandle[MISSION_ID])

    init {
        on<GuessMissionEvent.UpdateGuess> { event ->
            internalState.update { state ->
                state.copy(
                    guess = event.input
                )
            }
        }

        on<GuessMissionEvent.Confirm> {
            internalState.update { state ->
                state.copy(
                    status = GuessMissionState.Status.Loading
                )
            }
            viewModelScope.launch {
                missionRepository.guessMission(
                    missionId = missionId,
                    guess = internalState.value.guess
                )
                    .onSuccess {
                        internalState.update { state ->
                            state.copy(
                                status = GuessMissionState.Status.Success
                            )
                        }
                    }
                    .onFailure {
                        internalState.update { state ->
                            state.copy(
                                status = GuessMissionState.Status.Error
                            )
                        }
                    }
            }
        }
    }

    companion object {
        const val MISSION_ID = "missionId"
        const val IMAGE_URL = "imageUrl"
    }
}
