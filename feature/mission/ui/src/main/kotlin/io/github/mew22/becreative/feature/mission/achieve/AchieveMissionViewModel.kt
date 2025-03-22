package io.github.mew22.becreative.feature.mission.achieve

import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import io.github.mew22.becreative.core.monitoring.LogService
import io.github.mew22.becreative.core.ui.MviViewModel
import io.github.mew22.becreative.feature.mission.MissionRepository
import io.github.mew22.becreative.feature.mission.ui.BuildConfig
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI

class AchieveMissionViewModel(
    logService: LogService,
    val missionRepository: MissionRepository,
) : MviViewModel<AchieveMissionEvent, AchieveMissionState>(AchieveMissionState(), logService) {

    private var tempFileUri: Uri? = null

    init {
        listenMission()

        on<AchieveMissionEvent.CaptureImage.Saved> { event ->
            internalState.value.status.asSuccess()?.let { success ->
                if (success.tempFileUrl != null) {
                    tempFileUri = success.tempFileUrl
                    val source =
                        ImageDecoder.createSource(event.context.contentResolver, success.tempFileUrl)
                    val bitmap = ImageDecoder.decodeBitmap(source).asImageBitmap()
                    internalState.update { state ->
                        state.copy(
                            status = success.copy(
                                imageBitmap = bitmap,
                                tempFileUrl = null
                            )
                        )
                    }
                }
            }
        }

        on<AchieveMissionEvent.CompleteMission> {
            internalState.value.status.asSuccess()?.let {
                val uri = tempFileUri
                if (uri != null) {
                    internalState.update { state -> state.copy(status = AchieveMissionState.Status.Loading) }
                    viewModelScope.launch {
                        missionRepository.achieveMission(
                            missionId = it.mission.id,
                            imageUri = URI.create(uri.toString()),
                            canGuessMission = it.canGuessMission,
                        ).onSuccess {
                            internalState.update { state -> state.copy(status = AchieveMissionState.Status.MissionAccomplished) }
                        }.onFailure {
                            internalState.update { state -> state.copy(status = AchieveMissionState.Status.Error) }
                        }
                    }
                }
            }
        }

        on<AchieveMissionEvent.CaptureImage.Cancelled> {
            internalState.value.status.asSuccess()?.let {
                internalState.update { state ->
                    state.copy(
                        status = it.copy(
                            tempFileUrl = null,
                            imageBitmap = null
                        )
                    )
                }
            }
        }
        on<AchieveMissionEvent.CameraPermission.Granted> { event ->

            internalState.value.status.asSuccess()?.let {
                val tempFile = File.createTempFile(
                    "temp_image_file_",
                    ".jpg",
                    event.context.cacheDir
                )

                val uri = FileProvider.getUriForFile(
                    event.context,
                    "${BuildConfig.LIBRARY_PACKAGE_NAME}.provider",
                    tempFile
                )

                internalState.update { state ->
                    state.copy(
                        status = it.copy(
                            tempFileUrl = uri,
                            imageBitmap = null
                        )
                    )
                }
            }
        }

        on<AchieveMissionEvent.CameraPermission.Denied> {}

        on<AchieveMissionEvent.AllowGuessMissionChanged> {
            internalState.value.status.asSuccess()?.let {
                internalState.update { state -> state.copy(status = it.copy(canGuessMission = !it.canGuessMission)) }
            }
        }
    }

    private fun listenMission() {
        missionRepository.dailyMission.onEach {
            internalState.update { state ->
                state.copy(
                    status = AchieveMissionState.Status.Success(
                        it.toUi()
                    )
                )
            }
        }.launchIn(viewModelScope)
    }
}
