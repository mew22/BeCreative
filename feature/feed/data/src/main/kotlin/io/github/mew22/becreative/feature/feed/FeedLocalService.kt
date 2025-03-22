package io.github.mew22.becreative.feature.feed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedLocalService {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<FeedData>)

    @Query("select * from $TABLE_NAME")
    fun getFeeds(): Flow<List<FeedData>>

    companion object {
        const val TABLE_NAME = "feeds"
    }
}
