package io.github.mew22.becreative

import io.github.mew22.becreative.core.common.commonModule
import io.github.mew22.becreative.core.db.dbModule
import io.github.mew22.becreative.core.monitoring.monitoringModule
import io.github.mew22.becreative.feature.home.homeModule
import org.koin.core.KoinApplication

val koinConfig: KoinApplication.() -> Unit = {
    modules(
        commonModule,
        monitoringModule,
        dbModule,
    )
    modules(
        homeModule,
    )
}
