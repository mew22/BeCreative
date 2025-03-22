package io.github.mew22.becreative.feature.mission

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlin.random.Random

object MissionServiceMock : MissionService {

    val mission = MissionData(
        id = "5",
        title = "Snap a shadow or silhouette around you!",
        dateTime = Clock.System.now(),
        status = MissionData.MissionStatus.NOT_STARTED
    )

    val missionFlow: MutableStateFlow<MissionData?> = MutableStateFlow(null)

    override suspend fun achieveMission(
        missionId: String,
        imageUri: String,
        canGuessMission: Boolean
    ): Result<Unit> {
        delay(timeMillis = 2000)
        missionFlow.update { stateMission ->
            stateMission?.copy(
                status = if (canGuessMission) {
                    MissionData.MissionStatus.COMPLETED_HIDDEN
                } else {
                    MissionData.MissionStatus.COMPLETED
                }
            )
        }
        return Result.success(Unit)
    }

    override fun listenForDailyMission(): Flow<MissionData> = missionFlow
        .onEach { stateMission ->
            // Simulate delayed daily mission
            if (stateMission == null) {
                val delay = Random(Clock.System.now().epochSeconds)
                    .nextLong(from = 10000, until = 20000)
                delay(timeMillis = delay)
                missionFlow.emit(mission)
            }
        }
        .filterNotNull()

    override suspend fun guessMission(
        missionId: String,
        guess: String
    ): Result<Unit> {
        delay(timeMillis = 2000)
        return Result.success(Unit)
    }
}
