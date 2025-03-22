package io.github.mew22.becreative.feature.mission

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.github.mew22.becreative.feature.mission.guess.GuessMissionScreen
import io.github.mew22.becreative.feature.mission.guess.GuessMissionViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class GuessMissionRoute(val missionId: String, val imageUrl: String)

fun NavController.toGuessMissionRoute(
    missionId: String,
    imageUrl: String,
    navOptions: NavOptions? = null
) {
    navigate(GuessMissionRoute(missionId, imageUrl), navOptions)
}

fun NavGraphBuilder.guessMissionScreen(onBack: () -> Unit) {
    composable<GuessMissionRoute> {
        val viewModel = koinViewModel<GuessMissionViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        GuessMissionScreen(
            state = state,
            dispatch = viewModel::dispatch,
            onBack = onBack
        )
    }
}
