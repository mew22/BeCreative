package io.github.mew22.becreative.feature.mission

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.URI

class MissionRepositoryImpl(val missionService: MissionService) : MissionRepository {
    override val dailyMission: Flow<Mission> =
        missionService.listenForDailyMission().map { it.toDomain() }

    override suspend fun achieveMission(
        missionId: String,
        imageUri: URI,
        canGuessMission: Boolean
    ): Result<Unit> = missionService.achieveMission(missionId, imageUri.toString(), canGuessMission)

    override suspend fun guessMission(
        missionId: String,
        guess: String
    ): Result<Unit> = missionService.guessMission(missionId, guess)
}
