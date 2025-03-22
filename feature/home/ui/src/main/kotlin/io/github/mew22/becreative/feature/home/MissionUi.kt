package io.github.mew22.becreative.feature.home

import androidx.compose.runtime.Immutable
import io.github.mew22.becreative.feature.mission.Mission
import kotlinx.datetime.Instant

@Immutable
data class MissionUi(
    val id: String,
    val title: String,
    val dateTime: Instant,
    val status: MissionStatus = MissionStatus.NOT_STARTED
) {
    val showMissionInFeed: Boolean
        get() = status == MissionStatus.COMPLETED

    enum class MissionStatus {
        NOT_STARTED,
        COMPLETED,
        COMPLETED_HIDDEN
    }
}

fun Mission.toUi() = MissionUi(
    id = id,
    title = title,
    dateTime = dateTime,
    status = when (status) {
        Mission.MissionStatus.NOT_STARTED -> MissionUi.MissionStatus.NOT_STARTED
        Mission.MissionStatus.COMPLETED -> MissionUi.MissionStatus.COMPLETED
        Mission.MissionStatus.COMPLETED_HIDDEN -> MissionUi.MissionStatus.COMPLETED_HIDDEN
    }
)
