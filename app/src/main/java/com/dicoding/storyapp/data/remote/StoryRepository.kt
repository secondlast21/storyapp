package com.dicoding.storyapp.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.storyapp.data.remote.response.ListStoryItem
import com.dicoding.storyapp.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

class StoryRepository(
    private val apiService: ApiService
) {
    fun addNewStory(token: String, image: MultipartBody.Part, description: RequestBody): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val client = apiService.addNewStory(token, image, description)
            if (client.error) {
                emit(Result.Zero)
            } else {
                emit(Result.Success(client.message))
            }
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
            Log.d("xixixixi", e.message.toString())
        }
    }

    fun getLocation(token: String) : LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val client = apiService.locationAllStories(token, 50, 1)
            if (client.error) {
                emit(Result.Zero)
            } else {
                emit(Result.Success(client.listStory))
                Log.d("CERITA", client.listStory.toString())
            }
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
            Log.d("xixixixi", e.message.toString())
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstanceStory(
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService)
            }.also { instance = it }
    }
}