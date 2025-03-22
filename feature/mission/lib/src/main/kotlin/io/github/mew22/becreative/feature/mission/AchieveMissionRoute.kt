package io.github.mew22.becreative.feature.mission

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.github.mew22.becreative.feature.mission.achieve.AchieveMissionScreen
import io.github.mew22.becreative.feature.mission.achieve.AchieveMissionViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class AchieveMissionRoute(val missionId: String)

fun NavController.toAchieveMissionRoute(missionId: String, navOptions: NavOptions? = null) {
    navigate(AchieveMissionRoute(missionId), navOptions)
}

fun NavGraphBuilder.achieveMissionScreen(onBack: () -> Unit) {
    composable<AchieveMissionRoute> {
        val viewModel = koinViewModel<AchieveMissionViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        AchieveMissionScreen(
            state = state,
            dispatch = viewModel::dispatch,
            onBack = onBack
        )
    }
}
