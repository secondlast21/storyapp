package com.dicoding.storyapp.data.remote.retrofit

import com.dicoding.storyapp.data.remote.response.*
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register (
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login (
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

    @GET("stories")
    suspend fun testAllStories (
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int
    ) : ListStoryResponse

    @GET("stories")
    suspend fun locationAllStories (
        @Header("Authorization") token: String,
        @Query("size") size: Int,
        @Query("location") location: Int = 1
    ) : ListStoryResponse

    @Multipart
    @POST("stories")
    suspend fun addNewStory (
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ) : AddNewResponse
}