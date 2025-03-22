package io.github.mew22.becreative.feature.feed

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class FeedRepositoryImpl(
    private val feedLocalService: FeedLocalService,
    private val feedRemoteService: FeedRemoteService
) : FeedRepository {
    override val feeds: Flow<List<Feed>> = feedLocalService.getFeeds()
        .mapNotNull { feeds ->
            if (feeds.isEmpty()) {
                fetchFeeds()
                null
            } else {
                feeds.map { it.toDomain() }
            }
        }

    override suspend fun fetchFeeds(): Result<List<Feed>> =
        feedRemoteService.fetchFeeds().onSuccess {
            feedLocalService.insertAll(it)
        }.mapCatching { feeds -> feeds.map { it.toDomain() } }
}
