package io.github.mew22.becreative.feature.home

import androidx.compose.runtime.Immutable

@Immutable
data class HomeState(
    val status: Status = Status.Loading,
    val isRefreshing: Boolean = false,
) {
    sealed class Status {
        data object Loading : Status()

        @Immutable
        data object Success : Status()

        data object Error : Status()
    }
}
