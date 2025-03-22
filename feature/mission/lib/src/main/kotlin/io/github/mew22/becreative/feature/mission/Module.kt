package io.github.mew22.becreative.feature.mission

import io.github.mew22.becreative.feature.mission.achieve.AchieveMissionViewModel
import io.github.mew22.becreative.feature.mission.guess.GuessMissionViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val missionModule = module {
    factory<MissionService> { MissionServiceMock }
    factory<MissionRepository> { MissionRepositoryImpl(get()) }
    viewModelOf(::AchieveMissionViewModel)
    viewModelOf(::GuessMissionViewModel)
}
