package io.github.mew22.becreative.feature.feed

import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    val feeds: Flow<List<Feed>>
    suspend fun fetchFeeds(): Result<List<Feed>>
}
