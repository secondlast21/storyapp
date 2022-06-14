package com.dicoding.storyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.data.remote.StoryRepository

class MapsViewModel (
    private val storyRepository: StoryRepository
): ViewModel() {
    fun getLocation(token: String) = storyRepository.getLocation(token)
}