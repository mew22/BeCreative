package io.github.mew22.becreative.feature.home

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object HomeRoute

fun NavController.toHomeRoute(navOptions: NavOptions? = null) {
    navigate(HomeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    toGuessMission: (missionId: String, imageUrl: String) -> Unit,
    toAchieveMission: (missionId: String) -> Unit,
) {
    composable<HomeRoute> {
        val viewModel = koinViewModel<HomeViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        HomeScreen(
            state = state,
            toGuessMission = toGuessMission,
            toAchieveMission = toAchieveMission
        )
    }
}
