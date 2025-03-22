package io.github.mew22.becreative.feature.mission.achieve

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap

@Immutable
data class AchieveMissionState(
    val status: Status = Status.Loading,
    val isRefreshing: Boolean = false,
) {
    sealed class Status {
        data object Loading : Status()

        @Immutable
        data class Success(
            val mission: AchieveMissionUi,
            val tempFileUrl: Uri? = null,
            val imageBitmap: ImageBitmap? = null,
            val canGuessMission: Boolean = true,
        ) : Status()

        data object MissionAccomplished : Status()

        data object Error : Status()
    }
}

fun AchieveMissionState.Status.asSuccess() = this as? AchieveMissionState.Status.Success
