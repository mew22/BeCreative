package io.github.mew22.becreative.core.common

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

@Suppress("InjectDispatcher")
val commonModule = module {
    single { Dispatchers.IO }
}
