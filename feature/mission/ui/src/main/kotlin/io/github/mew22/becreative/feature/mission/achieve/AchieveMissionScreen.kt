package io.github.mew22.becreative.feature.mission.achieve

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import io.github.mew22.becreative.core.ui.component.DefaultBackAction
import io.github.mew22.becreative.core.ui.component.ErrorScreen
import io.github.mew22.becreative.core.ui.component.ErrorScreenDefaultActions
import io.github.mew22.becreative.core.ui.component.LoadingScreen
import io.github.mew22.becreative.core.ui.component.NavBar
import io.github.mew22.becreative.core.ui.component.ScrollableContent
import io.github.mew22.becreative.core.ui.designsystem.LocalThemeColors
import io.github.mew22.becreative.core.ui.designsystem.LocalThemeDimens
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun AchieveMissionScreen(
    state: AchieveMissionState,
    dispatch: (AchieveMissionEvent) -> Unit,
    onBack: () -> Unit,
) {
    val status = state.status
    when (status) {
        is AchieveMissionState.Status.Loading -> {
            LoadingScreen()
        }

        is AchieveMissionState.Status.Error -> {
            ErrorScreen(
                actions = {
                    ErrorScreenDefaultActions(
                        onPrimaryActionClick = { },
                        onSecondaryActionClick = { },
                    )
                }
            )
        }

        is AchieveMissionState.Status.MissionAccomplished -> {
            LaunchedEffect(status) {
                onBack()
            }
        }

        is AchieveMissionState.Status.Success -> {
            MissionView(
                state = status,
                dispatch = dispatch,
                onBack = onBack,
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Suppress("ModifierMissing")
@Composable
fun MissionView(
    state: AchieveMissionState.Status.Success,
    dispatch: (AchieveMissionEvent) -> Unit,
    onBack: () -> Unit,
) {
    val context = LocalContext.current.applicationContext
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isImageSaved ->
            if (isImageSaved) {
                dispatch(AchieveMissionEvent.CaptureImage.Saved(context))
            } else {
                dispatch(AchieveMissionEvent.CaptureImage.Cancelled)
            }
        }
    Scaffold(
        topBar = {
            NavBar(
                title = "Your mission",
                navigationAction = DefaultBackAction(onBack)
            )
        }
    ) { padding ->
        ScrollableContent(
            modifier = Modifier
                .padding(padding)
                .padding(LocalThemeDimens.current.standard100),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShowMissionCard(mission = state.mission)

            if (state.imageBitmap != null) {
                ShowCapturedImage(
                    imageBitmap = state.imageBitmap,
                )
            }

            if (state.imageBitmap != null) {
                ShowGuessMissionToggle(
                    checked = state.canGuessMission,
                    onCheckedChange = { dispatch(AchieveMissionEvent.AllowGuessMissionChanged) },
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            ShowActionIcon(
                dispatch = dispatch,
                showCheckButton = state.imageBitmap != null,
            )

            LaunchedEffect(key1 = state.tempFileUrl) {
                state.tempFileUrl?.let {
                    cameraLauncher.launch(it)
                }
            }
        }
    }
}

@Composable
private fun ShowMissionCard(
    mission: AchieveMissionUi,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(LocalThemeDimens.current.standard100),
    ) {
        Text(
            text = mission.title,
            modifier = Modifier.padding(LocalThemeDimens.current.standard100)
        )
    }
}

@Composable
private fun ShowCapturedImage(
    imageBitmap: ImageBitmap,
    modifier: Modifier = Modifier,
) {
    Image(
        bitmap = imageBitmap,
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(LocalThemeDimens.current.standard100))
            .border(
                LocalThemeDimens.current.standard25,
                LocalThemeColors.current.neutrals.grey400ViewBorder,
                RoundedCornerShape(LocalThemeDimens.current.standard100)
            ),
    )
}

@Composable
private fun ShowGuessMissionToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(LocalThemeDimens.current.standard100),
    ) {
        Row(
            modifier = Modifier
                .padding(LocalThemeDimens.current.standard100)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
            )
            Spacer(modifier = Modifier.height(LocalThemeDimens.current.standard100))
            Text(
                text = "Check this to allow your friends to guess your challenge!",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ShowActionIcon(
    dispatch: (AchieveMissionEvent) -> Unit,
    showCheckButton: Boolean,
) {
    val context = LocalContext.current.applicationContext
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
                dispatch(AchieveMissionEvent.CameraPermission.Granted(context))
            } else {
                dispatch(AchieveMissionEvent.CameraPermission.Denied)
            }
        }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(LocalThemeColors.current.oranges.orange400Primary)
                .padding(LocalThemeDimens.current.standard100),
            onClick = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            },
        ) {
            Icon(
                imageVector = Camera,
                contentDescription = null,
                modifier = Modifier.size(LocalThemeDimens.current.standard300),
            )
        }

        if (showCheckButton) {
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(LocalThemeColors.current.greens.green400Brand)
                    .padding(LocalThemeDimens.current.standard100),
                onClick = { dispatch(AchieveMissionEvent.CompleteMission) },
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(LocalThemeDimens.current.standard300),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MissionViewPreview() {
    MissionView(
        state = AchieveMissionState.Status.Success(
            mission = AchieveMissionUi(
                id = "1",
                title = "Mission title",
                dateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
            ),
            imageBitmap = null,
            tempFileUrl = null,
            canGuessMission = true,
        ),
        onBack = {},
        dispatch = {},
    )
}
