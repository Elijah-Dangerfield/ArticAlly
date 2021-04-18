package com.dangerfield.artically.data.cache.db

import androidx.room.*
import com.dangerfield.artically.data.cache.model.TopHeadlineCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopHeadlinesDao {
    /**
     * gets all cached top headlines
     */
    @Query("SELECT * from TOP_HEADLINE")
    fun getTopHeadlines(): Flow<List<TopHeadlineCacheEntity>>

    /**
     * inserts a list of top headlines
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopHeadlines(topHeadlines: List<TopHeadlineCacheEntity>)


    /**
     * Deletes all previous top headlines
     * inserts all new top headlines
     */
    @Transaction
    suspend fun replaceTopHeadlines(headlines: List<TopHeadlineCacheEntity>) {
        deleteTopHeadlines()
        insertTopHeadlines(headlines)
    }

    /**
     * removes all top headlines
     */
    @Query("DELETE from TOP_HEADLINE")
    suspend fun deleteTopHeadlines()



}