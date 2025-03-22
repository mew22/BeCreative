package io.github.mew22.becreative.feature.mission.guess

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import io.github.mew22.becreative.core.ui.component.DefaultBackAction
import io.github.mew22.becreative.core.ui.component.ErrorScreen
import io.github.mew22.becreative.core.ui.component.ErrorScreenDefaultActions
import io.github.mew22.becreative.core.ui.component.LoadingScreen
import io.github.mew22.becreative.core.ui.component.NavBar
import io.github.mew22.becreative.core.ui.component.ScrollableContent
import io.github.mew22.becreative.core.ui.component.button.PrimaryButton
import io.github.mew22.becreative.core.ui.designsystem.LocalThemeColors
import io.github.mew22.becreative.core.ui.designsystem.LocalThemeDimens

@Composable
fun GuessMissionScreen(
    state: GuessMissionState,
    dispatch: (GuessMissionEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        targetState = state.status,
        modifier = modifier,
    ) { status ->
        when (status) {
            is GuessMissionState.Status.Loading -> {
                LoadingScreen()
            }

            is GuessMissionState.Status.Error -> {
                ErrorScreen(
                    actions = {
                        ErrorScreenDefaultActions(
                            onPrimaryActionClick = { },
                            onSecondaryActionClick = { },
                        )
                    }
                )
            }

            is GuessMissionState.Status.Success -> {
                LaunchedEffect(status) {
                    onBack()
                }
            }

            is GuessMissionState.Status.Idle -> {
                GuessMissionView(
                    dispatch = dispatch,
                    onBack = onBack,
                    imageUrl = state.imageUrl,
                    guess = state.guess,
                )
            }
        }
    }
}

@Suppress("ModifierMissing")
@Composable
fun GuessMissionView(
    onBack: () -> Unit,
    dispatch: (GuessMissionEvent) -> Unit,
    imageUrl: String,
    guess: String,
) {
    Scaffold(
        topBar = {
            NavBar(
                title = "Guess Mission",
                navigationAction = DefaultBackAction(onBack)
            )
        },
    ) { padding ->
        ScrollableContent(
            modifier = Modifier
                .padding(padding)
                .padding(LocalThemeDimens.current.standard100),
            verticalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard100),
        ) {
            ShowSummary()
            ShowContent(
                dispatch = dispatch,
                imageUrl = imageUrl,
                guess = guess,
            )
            ShowConfirmButton(
                onClink = { dispatch(GuessMissionEvent.Confirm) },
                isEnabled = guess.isNotBlank(),
            )
        }
    }
}

@Composable
private fun ShowSummary() {
    Card {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalThemeDimens.current.standard100),
            text = "Guess the mission illustrated by this image"
        )
    }
}

@Composable
private fun ShowContent(
    dispatch: (GuessMissionEvent) -> Unit,
    imageUrl: String,
    guess: String,
) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalThemeDimens.current.standard100),
            verticalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard100),
        ) {
            ShowImage(imageUri = imageUrl)
            ShowGuessInput(
                input = guess,
                onValueChange = { input ->
                    dispatch(GuessMissionEvent.UpdateGuess(input))
                },
            )
        }
    }
}

@Composable
private fun ShowGuessInput(
    onValueChange: (String) -> Unit,
    input: String,
) {
    TextField(
        value = input,
        onValueChange = onValueChange,
        label = { Text("Guess Mission:") },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun ShowImage(imageUri: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUri,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .fillMaxWidth()
            .border(
                LocalThemeDimens.current.standard25,
                LocalThemeColors.current.neutrals.grey400ViewBorder,
                RoundedCornerShape(LocalThemeDimens.current.standard200)
            )
            .clip(RoundedCornerShape(LocalThemeDimens.current.standard200))
    )
}

@Composable
private fun ShowConfirmButton(
    onClink: () -> Unit,
    isEnabled: Boolean,
) {
    PrimaryButton(
        text = "Confirm",
        onClick = onClink,
        enabled = isEnabled,
    )
}

@Preview(showBackground = true)
@Composable
private fun GuessMissionViewPreview() {
    GuessMissionView(
        onBack = {},
        imageUrl = "",
        guess = "",
        dispatch = {},
    )
}
