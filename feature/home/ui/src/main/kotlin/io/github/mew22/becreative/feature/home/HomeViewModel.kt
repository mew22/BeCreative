package io.github.mew22.becreative.feature.home

import io.github.mew22.becreative.core.monitoring.LogService
import io.github.mew22.becreative.core.ui.MviViewModel

class HomeViewModel(
    logService: LogService,
) : MviViewModel<HomeEvent, HomeState>(HomeState(), logService)
