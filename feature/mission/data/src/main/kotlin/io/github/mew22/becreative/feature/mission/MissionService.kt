package io.github.mew22.becreative.feature.mission

import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime

interface MissionService {
    @OptIn(ExperimentalTime::class)
    suspend fun achieveMission(
        missionId: String,
        imageUri: String,
        canGuessMission: Boolean
    ): Result<Unit>

    fun listenForDailyMission(): Flow<MissionData>

    suspend fun guessMission(
        missionId: String,
        guess: String
    ): Result<Unit>
}
