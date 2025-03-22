package io.github.mew22.becreative.feature.home

import androidx.compose.runtime.Immutable
import io.github.mew22.becreative.feature.feed.ChainedNumber
import io.github.mew22.becreative.feature.feed.Feed
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Immutable
sealed class FeedUi {
    abstract val id: String
    abstract val username: String
    abstract val postedTime: LocalDateTime
    abstract val feedIcon: String
    abstract val canClick: Boolean

    @Immutable
    data class ChallengeFeed(
        override val id: String,
        override val username: String,
        override val postedTime: LocalDateTime,
        val mission: MissionUi,
        val imageUri: String,
    ) : FeedUi() {
        override val feedIcon = "\uD83D\uDDBC\uFE0F"
        override val canClick: Boolean
            get() = this.mission.showMissionInFeed.not()
    }

    @Immutable
    data class PhotoChainedFeed(
        override val id: String,
        override val username: String,
        override val postedTime: LocalDateTime,
        val parentId: String,
        val chainedNumber: Int,
        val imageUri: String,
    ) : FeedUi() {
        override val feedIcon = "\uD83D\uDCF7"
        override val canClick: Boolean = false
        val chainPosition = "$chainedNumber/${ChainedNumber.MAX_CHAINS}"
    }

    @Immutable
    data class GuessFeed(
        override val id: String,
        override val username: String,
        override val postedTime: LocalDateTime,
        val parentId: String,
        val description: String,
    ) : FeedUi() {
        override val feedIcon = "\uD83C\uDFAF"
        override val canClick: Boolean = false
    }
}

fun Feed.ChallengeFeed.toUi() = FeedUi.ChallengeFeed(
    id = id,
    username = username,
    postedTime = postedTime.toLocalDateTime(TimeZone.currentSystemDefault()),
    imageUri = imageUri.toString(),
    mission = mission.toUi()
)

fun Feed.PhotoChainedFeed.toUi() = FeedUi.PhotoChainedFeed(
    id = id,
    username = username,
    postedTime = postedTime.toLocalDateTime(TimeZone.currentSystemDefault()),
    parentId = parentId,
    chainedNumber = chainedNumber.input,
    imageUri = imageUri.toString(),
)

fun Feed.GuessFeed.toUi() = FeedUi.GuessFeed(
    id = id,
    username = username,
    postedTime = postedTime.toLocalDateTime(TimeZone.currentSystemDefault()),
    parentId = parentId,
    description = description,
)

fun Feed.toUi() = when (this) {
    is Feed.ChallengeFeed -> toUi()
    is Feed.PhotoChainedFeed -> toUi()
    is Feed.GuessFeed -> toUi()
}
