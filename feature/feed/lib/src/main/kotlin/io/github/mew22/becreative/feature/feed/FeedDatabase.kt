package io.github.mew22.becreative.feature.feed

interface FeedDatabase {
    fun feedsDao(): FeedLocalService
}
