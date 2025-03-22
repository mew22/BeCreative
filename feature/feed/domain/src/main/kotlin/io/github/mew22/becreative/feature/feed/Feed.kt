package io.github.mew22.becreative.feature.feed

import io.github.mew22.becreative.feature.mission.Mission
import kotlinx.datetime.Instant
import java.net.URI

sealed interface Feed {
    val id: String
    val username: String
    val postedTime: Instant

    data class ChallengeFeed(
        override val id: String,
        override val username: String,
        override val postedTime: Instant,
        val mission: Mission,
        val imageUri: URI,
    ) : Feed

    data class PhotoChainedFeed(
        override val id: String,
        override val username: String,
        override val postedTime: Instant,
        val parentId: String,
        val chainedNumber: ChainedNumber,
        val imageUri: URI,
    ) : Feed

    data class GuessFeed(
        override val id: String,
        override val username: String,
        override val postedTime: Instant,
        val parentId: String,
        val description: String,
    ) : Feed
}
