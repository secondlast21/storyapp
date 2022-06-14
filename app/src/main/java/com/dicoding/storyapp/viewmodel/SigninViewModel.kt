package com.dicoding.storyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.data.remote.MainRepository

class SigninViewModel(
    private val mainRepository: MainRepository
): ViewModel() {
    fun signIn(email: String, password: String) = mainRepository.signIn(email, password)
}