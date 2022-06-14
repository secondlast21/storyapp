package com.dicoding.storyapp.di

import com.dicoding.storyapp.data.remote.StoryRepository
import com.dicoding.storyapp.data.remote.retrofit.ApiConfig

object StoryInjection {
    fun provideStoryRepository(): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstanceStory(apiService)
    }
}