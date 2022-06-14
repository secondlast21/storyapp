package com.dicoding.storyapp.data.retrofit

import com.dicoding.storyapp.data.remote.response.AddNewResponse
import com.dicoding.storyapp.data.remote.response.ListStoryResponse
import com.dicoding.storyapp.data.remote.response.LoginResponse
import com.dicoding.storyapp.data.remote.response.RegisterResponse
import com.dicoding.storyapp.data.remote.retrofit.ApiService
import com.dicoding.storyapp.utils.DataDummy
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApiService : ApiService {
    private val dummySignup = DataDummy.generateDummySignupTrue()
    private val dummySignin = DataDummy.generateDummySignin()
    private val dummyListStories = DataDummy.generateDummyListStories()
    private val dummyAddStories = DataDummy.generateDummyAddStories()

    override suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return dummySignup
    }

    override suspend fun login(email: String, password: String): LoginResponse {
        return LoginResponse(dummySignin, false, "success")
    }

    override suspend fun testAllStories(
        token: String,
        page: Int,
        size: Int,
        location: Int,
    ): ListStoryResponse {
        return ListStoryResponse(dummyListStories, false, "success")
    }

    override suspend fun locationAllStories(
        token: String,
        size: Int,
        location: Int,
    ): ListStoryResponse {
        return ListStoryResponse(dummyListStories, false, "success")
    }

    override suspend fun addNewStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
    ): AddNewResponse {
        return dummyAddStories
    }
}