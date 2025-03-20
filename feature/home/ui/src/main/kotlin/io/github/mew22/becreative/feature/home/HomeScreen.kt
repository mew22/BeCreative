package io.github.mew22.becreative.feature.home

import androidx.compose.animation.Crossfade
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.mew22.becreative.core.ui.component.ErrorScreen
import io.github.mew22.becreative.core.ui.component.ErrorScreenDefaultActions
import io.github.mew22.becreative.core.ui.component.LoadingScreen

@Composable
fun HomeScreen(
    state: HomeState,
    dispatch: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        targetState = state.status,
        modifier = modifier,
    ) { status ->
        when (status) {
            is HomeState.Status.Loading -> {
                LoadingScreen()
            }

            is HomeState.Status.Error -> {
                ErrorScreen(
                    actions = {
                        ErrorScreenDefaultActions(
                            onPrimaryActionClick = { },
                            onSecondaryActionClick = { },
                        )
                    }
                )
            }

            is HomeState.Status.Success -> {
                HomeView()
            }
        }
    }
}

@Composable
fun HomeView() {
    Text("TODO")
}

@Preview(showBackground = true)
@Composable
private fun HomeViewPreview() {
    HomeView()
}
