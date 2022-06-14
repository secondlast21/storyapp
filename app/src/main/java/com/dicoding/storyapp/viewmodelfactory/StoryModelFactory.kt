package com.dicoding.storyapp.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.storyapp.data.remote.StoryRepository
import com.dicoding.storyapp.di.StoryInjection
import com.dicoding.storyapp.viewmodel.AddViewModel
import com.dicoding.storyapp.viewmodel.MapsViewModel

class StoryModelFactory(private val storyRepository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            return AddViewModel(storyRepository) as T
        } else if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(storyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: StoryModelFactory? = null
        fun getInstanceStory(): StoryModelFactory =
            instance ?: synchronized(this) {
                instance ?: StoryModelFactory(StoryInjection.provideStoryRepository())
            }.also { instance = it }
    }
}