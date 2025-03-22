package io.github.mew22.becreative.feature.feed

interface FeedRemoteService {
    suspend fun fetchFeeds(): Result<List<FeedData>>
}
