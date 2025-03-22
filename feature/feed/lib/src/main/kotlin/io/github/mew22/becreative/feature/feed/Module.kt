package io.github.mew22.becreative.feature.feed

import org.koin.dsl.module

val feedModule = module {
    factory<FeedLocalService> { get<FeedDatabase>().feedsDao() }
    factory<FeedRemoteService> { FeedRemoteServiceMock }
    factory<FeedRepository> { FeedRepositoryImpl(get(), get()) }
}
