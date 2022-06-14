package com.dicoding.storyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.data.remote.MainRepository

class SignupViewModel(
    private val mainRepository: MainRepository
): ViewModel() {
    fun signUp(name: String, email: String, password: String) = mainRepository.signUp(name, email, password)
}