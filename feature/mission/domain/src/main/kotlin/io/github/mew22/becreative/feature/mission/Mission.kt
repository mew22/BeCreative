package io.github.mew22.becreative.feature.mission

import kotlinx.datetime.Instant

data class Mission(
    val id: String,
    val title: String,
    val dateTime: Instant,
    val status: MissionStatus,
) {
    enum class MissionStatus {
        NOT_STARTED,
        COMPLETED,
        COMPLETED_HIDDEN
    }
}
