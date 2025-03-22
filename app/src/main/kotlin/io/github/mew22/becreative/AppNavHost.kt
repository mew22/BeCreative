package io.github.mew22.becreative

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.github.mew22.becreative.feature.home.HomeRoute
import io.github.mew22.becreative.feature.home.homeScreen
import io.github.mew22.becreative.feature.mission.achieveMissionScreen
import io.github.mew22.becreative.feature.mission.guessMissionScreen
import io.github.mew22.becreative.feature.mission.toAchieveMissionRoute
import io.github.mew22.becreative.feature.mission.toGuessMissionRoute
import kotlin.reflect.KClass

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: KClass<*> = HomeRoute::class,
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        homeScreen(
            toAchieveMission = navController::toAchieveMissionRoute,
            toGuessMission = navController::toGuessMissionRoute,
        )
        achieveMissionScreen(onBack = navController::popBackStack)
        guessMissionScreen(onBack = navController::popBackStack)
    }
}
