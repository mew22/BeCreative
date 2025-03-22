package io.github.mew22.becreative

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.mew22.becreative.feature.feed.FeedData
import io.github.mew22.becreative.feature.feed.FeedDatabase
import io.github.mew22.becreative.feature.mission.MissionData

@Database(
    version = 1,
    entities = [FeedData::class],
    exportSchema = true
)
@TypeConverters(
    MissionData.Converter::class,
)
abstract class Database : RoomDatabase(), FeedDatabase
