package io.github.mew22.becreative.feature.mission.achieve

import android.content.Context

sealed class AchieveMissionEvent {
    sealed class CameraPermission : AchieveMissionEvent() {
        data class Granted(val context: Context) : CameraPermission()
        data object Denied : CameraPermission()
    }
    sealed class CaptureImage : AchieveMissionEvent() {
        data class Saved(val context: Context) : CaptureImage()
        data object Cancelled : CaptureImage()
    }

    data object CompleteMission : AchieveMissionEvent()
    data object AllowGuessMissionChanged : AchieveMissionEvent()
}
