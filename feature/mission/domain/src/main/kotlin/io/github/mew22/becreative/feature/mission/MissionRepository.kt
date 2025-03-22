package io.github.mew22.becreative.feature.mission

import kotlinx.coroutines.flow.Flow
import java.net.URI

interface MissionRepository {
    val dailyMission: Flow<Mission>
    suspend fun achieveMission(
        missionId: String,
        imageUri: URI,
        canGuessMission: Boolean
    ): Result<Unit>

    suspend fun guessMission(
        missionId: String,
        guess: String
    ): Result<Unit>
}
