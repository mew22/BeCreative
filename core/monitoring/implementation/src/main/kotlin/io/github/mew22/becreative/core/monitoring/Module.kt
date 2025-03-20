package io.github.mew22.becreative.core.monitoring

import org.koin.dsl.module

val monitoringModule = module {
    single<LogService> { LogServiceImpl() }
}
