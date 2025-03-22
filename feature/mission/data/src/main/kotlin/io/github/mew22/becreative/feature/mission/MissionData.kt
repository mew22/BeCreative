package io.github.mew22.becreative.feature.mission

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class MissionData(
    val id: String,
    val title: String,
    val dateTime: Instant,
    val status: MissionStatus,
) {
    object Converter {
        @TypeConverter
        fun toJson(data: MissionData): String = Json.encodeToString(data)

        @TypeConverter
        fun toObject(data: String): MissionData = Json.decodeFromString<MissionData>(data)
    }

    enum class MissionStatus {
        NOT_STARTED,
        COMPLETED,
        COMPLETED_HIDDEN
    }
}

fun MissionData.toDomain(): Mission = Mission(
    id = id,
    title = title,
    dateTime = dateTime,
    status = when (status) {
        MissionData.MissionStatus.NOT_STARTED -> Mission.MissionStatus.NOT_STARTED
        MissionData.MissionStatus.COMPLETED -> Mission.MissionStatus.COMPLETED
        MissionData.MissionStatus.COMPLETED_HIDDEN -> Mission.MissionStatus.COMPLETED_HIDDEN
    }
)
