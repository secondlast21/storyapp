package com.dicoding.storyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.storyapp.data.remote.RemoteRepository
import com.dicoding.storyapp.data.remote.response.ListStoryItem

class MainViewModel(
    private val remoteRepository: RemoteRepository
): ViewModel() {
    fun getStory(token: String) : LiveData<PagingData<ListStoryItem>> = remoteRepository.getStory(token).cachedIn(viewModelScope)
}