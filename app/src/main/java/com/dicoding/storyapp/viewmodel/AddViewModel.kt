package com.dicoding.storyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.data.remote.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddViewModel (
    private val storyRepository: StoryRepository
): ViewModel() {
    fun addNewStory(token: String, image: MultipartBody.Part, description: RequestBody) = storyRepository.addNewStory(token, image, description)
}