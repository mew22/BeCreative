package io.github.mew22.becreative.feature.home

import androidx.lifecycle.viewModelScope
import io.github.mew22.becreative.core.monitoring.LogService
import io.github.mew22.becreative.core.ui.MviViewModel
import io.github.mew22.becreative.feature.feed.FeedRepository
import io.github.mew22.becreative.feature.mission.MissionRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class HomeViewModel(
    logService: LogService,
    val feedRepository: FeedRepository,
    val missionRepository: MissionRepository,
) : MviViewModel<HomeEvent, HomeState>(HomeState(), logService) {

    init {
        listenData()
    }

    private fun listenData() {
        feedRepository.feeds.onEach {
            internalState.update { state ->
                state.copy(
                    status = HomeState.Status.Success(
                        feeds = it.map { it.toUi() }
                            .toImmutableList(),
                    )
                )
            }
        }.launchIn(viewModelScope)

        missionRepository.dailyMission.onEach { mission ->
            internalState.value.status.asSuccessOrNull()?.let { status ->
                internalState.update { state ->
                    state.copy(
                        status = status.copy(
                            mission = mission.toUi()
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
