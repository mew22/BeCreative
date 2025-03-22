package io.github.mew22.becreative

import io.github.mew22.becreative.core.common.commonModule
import io.github.mew22.becreative.core.db.DatabaseHelper
import io.github.mew22.becreative.core.db.dbModule
import io.github.mew22.becreative.core.monitoring.monitoringModule
import io.github.mew22.becreative.feature.feed.FeedDatabase
import io.github.mew22.becreative.feature.feed.feedModule
import io.github.mew22.becreative.feature.home.homeModule
import io.github.mew22.becreative.feature.mission.missionModule
import org.koin.core.KoinApplication
import org.koin.dsl.binds
import org.koin.dsl.module

val koinConfig: KoinApplication.() -> Unit = {
    modules(
        commonModule,
        monitoringModule,
        dbModule,
        databaseModule
    )
    modules(
        homeModule,
        feedModule,
        missionModule,
    )
}

val databaseModule = module {
    single<Database> {
        get<DatabaseHelper>().create(
            Database::class.java,
            "database.db"
        )
    }.binds(arrayOf(FeedDatabase::class))
}
