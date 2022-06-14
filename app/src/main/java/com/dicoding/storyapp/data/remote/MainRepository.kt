package com.dicoding.storyapp.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.storyapp.data.remote.response.*
import com.dicoding.storyapp.data.remote.retrofit.ApiService
import java.lang.Exception

class MainRepository (
    private val apiService: ApiService
) {

    fun signUp(name: String = "", email: String = "", password: String = ""): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val client = apiService.register(name, email, password)
            if (client.error) {
                emit(Result.Zero)
                Log.d("Error", client.message)
            } else {
                emit(Result.Success(client.message))
            }
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun signIn(email: String = "", password: String = ""): LiveData<Result<LoginResult>> = liveData {
        emit(Result.Loading)
        try {
            val client = apiService.login(email, password)
            if (client.error) {
                emit(Result.Zero)
            } else {
                emit(Result.Success(client.loginResult))
            }
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }


    companion object {
        @Volatile
        private var instance: MainRepository? = null
        fun getInstance(
            apiService: ApiService
        ): MainRepository =
            instance ?: synchronized(this) {
                instance ?: MainRepository(apiService)
            }.also { instance = it }
    }
}