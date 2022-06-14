package com.dicoding.storyapp.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.storyapp.data.remote.RemoteRepository
import com.dicoding.storyapp.di.RemoteInjection
import com.dicoding.storyapp.viewmodel.MainViewModel

class RemoteModelFactory(private val remoteRepository: RemoteRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(remoteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: RemoteModelFactory? = null
        fun getInstanceStory(context: Context): RemoteModelFactory =
            instance ?: synchronized(this) {
                instance ?: RemoteModelFactory(RemoteInjection.provideRepository(context))
            }.also { instance = it }
    }
}