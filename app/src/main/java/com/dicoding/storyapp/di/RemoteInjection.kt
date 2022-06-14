package com.dicoding.storyapp.di

import android.content.Context
import com.dicoding.storyapp.data.remote.RemoteRepository
import com.dicoding.storyapp.data.remote.retrofit.ApiConfig
import com.dicoding.storyapp.database.room.StoryDatabase

object RemoteInjection {
    fun provideRepository(context: Context): RemoteRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return RemoteRepository(database, apiService)
    }
}