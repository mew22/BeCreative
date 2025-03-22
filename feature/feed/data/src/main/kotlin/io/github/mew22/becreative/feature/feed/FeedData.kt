package io.github.mew22.becreative.feature.feed

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.mew22.becreative.feature.feed.FeedData.FeedTypeData
import io.github.mew22.becreative.feature.feed.FeedLocalService.Companion.TABLE_NAME
import io.github.mew22.becreative.feature.mission.MissionData
import io.github.mew22.becreative.feature.mission.toDomain
import kotlinx.datetime.Instant
import java.net.URI

@Entity(tableName = TABLE_NAME)
data class FeedData(
    @PrimaryKey val id: String,
    val type: FeedTypeData,
    val username: String,
    val postedTime: String,
    val parentId: String? = null,
    val mission: MissionData? = null,
    val chainedNumber: Int? = null,
    val imageUri: String? = null,
    val description: String? = null,
) {
    enum class FeedTypeData { CHALLENGE, PHOTO_CHAINED, GUESS }
}

fun FeedData.toDomain(): Feed = when (this.type) {
    FeedTypeData.CHALLENGE -> Feed.ChallengeFeed(
        id = id,
        username = username,
        postedTime = Instant.parse(postedTime),
        mission = mission?.toDomain() ?: throw IllegalArgumentException("mission missing"),
        imageUri = imageUri?.let { URI.create(it) }
            ?: throw IllegalArgumentException("imageUri missing"),
    )

    FeedTypeData.PHOTO_CHAINED -> Feed.PhotoChainedFeed(
        id = id,
        username = username,
        postedTime = Instant.parse(postedTime),
        chainedNumber = ChainedNumber.getOrThrow(
            chainedNumber ?: throw IllegalArgumentException("chainedNumber missing")
        ),
        parentId = parentId ?: throw IllegalArgumentException("parentId missing"),
        imageUri = imageUri?.let { URI.create(it) }
            ?: throw IllegalArgumentException("imageUri missing"),
    )

    FeedTypeData.GUESS -> Feed.GuessFeed(
        id = id,
        username = username,
        postedTime = Instant.parse(postedTime),
        parentId = parentId ?: throw IllegalArgumentException("parentId missing"),
        description = description ?: throw IllegalArgumentException("description missing"),
    )
}
