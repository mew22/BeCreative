package io.github.mew22.becreative.feature.mission

import org.koin.dsl.module

val missionModule = module {
    factory<MissionService> { MissionServiceMock }
    factory<MissionRepository> { MissionRepositoryImpl(get()) }
}
