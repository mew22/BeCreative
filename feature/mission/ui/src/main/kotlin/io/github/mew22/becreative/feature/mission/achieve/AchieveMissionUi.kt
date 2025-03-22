package io.github.mew22.becreative.feature.mission.achieve

import androidx.compose.runtime.Immutable
import io.github.mew22.becreative.feature.mission.Mission
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Immutable
data class AchieveMissionUi(
    val id: String,
    val title: String,
    val dateTime: LocalTime,
)

fun Mission.toUi() = AchieveMissionUi(
    id = id,
    title = title,
    dateTime = dateTime.toLocalDateTime(TimeZone.currentSystemDefault()).time,
)
