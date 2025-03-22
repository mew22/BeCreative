package io.github.mew22.becreative.feature.home

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class HomeState(
    val status: Status = Status.Loading,
    val isRefreshing: Boolean = false,
) {
    sealed class Status {
        data object Loading : Status()

        @Immutable
        data class Success(
            val feeds: ImmutableList<FeedUi>,
            val mission: MissionUi? = null,
        ) : Status() {
            val isFeedVisible: Boolean
            get() = mission == null || mission.status != MissionUi.MissionStatus.NOT_STARTED
        }

        data object Error : Status()
    }
}

fun HomeState.Status.asSuccessOrNull() = this as? HomeState.Status.Success
