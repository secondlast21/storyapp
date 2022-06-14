package com.dicoding.storyapp.database.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.dicoding.storyapp.data.remote.response.ListStoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<ListStoryItem>)

    @Query("SELECT * FROM Story")
    fun getAllStory(): PagingSource<Int, ListStoryItem>

    @Query("SELECT * FROM Story")
    fun getLocation(): LiveData<List<ListStoryItem>>

    @Query("DELETE FROM Story")
    suspend fun deleteAll()
}