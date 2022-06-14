package com.dicoding.storyapp.di

import android.content.Context
import com.dicoding.storyapp.data.remote.MainRepository
import com.dicoding.storyapp.data.remote.retrofit.ApiConfig
import com.dicoding.storyapp.database.room.StoryDatabase

object Injection {
    fun provideRepository(): MainRepository {
        val apiService = ApiConfig.getApiService()
        return MainRepository.getInstance(apiService)
    }
}