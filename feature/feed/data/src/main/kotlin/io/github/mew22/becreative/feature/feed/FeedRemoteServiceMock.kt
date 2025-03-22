package io.github.mew22.becreative.feature.feed

import io.github.mew22.becreative.feature.feed.FeedData.FeedTypeData
import io.github.mew22.becreative.feature.mission.MissionData
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object FeedRemoteServiceMock : FeedRemoteService {

    val feeds = listOf(
        FeedData(
            id = "1",
            type = FeedTypeData.CHALLENGE,
            username = "Kalyagan",
            postedTime = Clock.System.now().minus(8.toDuration(DurationUnit.HOURS)).toString(),
            imageUri = "https://media.istockphoto.com/id/154232673/photo/blue-ridge-parkway-" +
                    "scenic-landscape-appalachian-mountains-ridges-sunset-layers.jpg?s=612x612" +
                    "&w=0&k=20&c=m2LZsnuJl6Un7oW4pHBH7s6Yr9-yB6pLkZ-8_vTj2M0=",
            mission = MissionData(
                id = "1",
                title = "Snap your morning view",
                dateTime = Clock.System.now(),
                status = MissionData.MissionStatus.COMPLETED
            )
        ),
        FeedData(
            id = "2",
            type = FeedTypeData.CHALLENGE,
            username = "Simplet",
            postedTime = Clock.System.now().minus(9.toDuration(DurationUnit.HOURS)).toString(),
            imageUri = "https://thumbs.dreamstime.com/b/omelette-meal-tomato-breakfast-" +
                    "vertical-flat-lay-omelette-meal-tomato-breakfast-vertical-flat-lay-fried" +
                    "-scrambled-fluffy-egg-138982409.jpg",
            mission = MissionData(
                id = "2",
                title = "Your breakfast in one snap!!",
                dateTime = Clock.System.now(),
                status = MissionData.MissionStatus.COMPLETED_HIDDEN
            )
        ),
        FeedData(
            id = "3",
            type = FeedTypeData.CHALLENGE,
            username = "Kia",
            postedTime = Clock.System.now().minus(7.toDuration(DurationUnit.HOURS)).toString(),
            imageUri = "https://media.istockphoto.com/id/455643329/photo/sunny-blue-sky.jpg?s=612" +
                    "x612&w=0&k=20&c=tzimmUP6yUW5XWToGfZO3S69I7QPUYr7pprx4ERklNw=",
            mission = MissionData(
                id = "3",
                title = "Snap the the color blue around you",
                dateTime = Clock.System.now(),
                status = MissionData.MissionStatus.COMPLETED_HIDDEN
            )
        ),
        FeedData(
            id = "4",
            type = FeedTypeData.PHOTO_CHAINED,
            username = "Joris",
            postedTime = Clock.System.now().minus(3.toDuration(DurationUnit.HOURS)).toString(),
            parentId = "2",
            imageUri = "https://st4.depositphotos.com/1761693/19863/i/450/depositphotos_19863" +
                    "4988-stock-photo-fried-eggs-bacon-sausages-vegetables.jpg",
            chainedNumber = 10,
        ),
        FeedData(
            id = "5",
            type = FeedTypeData.GUESS,
            username = "Gomar",
            parentId = "3",
            postedTime = Clock.System.now().minus(4.toDuration(DurationUnit.HOURS)).toString(),
            description = "The sky",
        ),
    )

    override suspend fun fetchFeeds(): Result<List<FeedData>> {
        delay(timeMillis = 2000)
        return Result.success(feeds)
    }
}
