package com.dicoding.storyapp.utils

import com.dicoding.storyapp.data.remote.response.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object DataDummy {
    fun generateDummyName() : String {
        return "faris"
    }

    fun generateDummyEmail() : String {
        return "ris@ris.com"
    }

    fun generateDummyPassword() : String {
        return "farisin"
    }

    fun generateDummySignin() : LoginResult {
        return LoginResult (
            "faris",
            "210202",
            "210202"
        )
    }

    fun generateDummySignupTrue() : RegisterResponse {
        return RegisterResponse(false, "success")
    }

    fun generateDummyPhoto() : MultipartBody.Part {
        return MultipartBody.Part.createFormData("DummyPhotoUrl", "https://story-api.dicoding.dev/images/stories/photos-1111111111111__aaaaaaa.jpg")
    }

    fun generateDummyDesc() : RequestBody {
        return "dummyDesc".toRequestBody("text/plain".toMediaType())
    }

    fun generateDummyToken() : String {
        return "eyJhasfdOiJIUzI1NiIsInwertI6IkpXVCJ9.eyJ234rySWQiOiJ1c567bWxJeFRxeE0xWVRraHhVNzYi12er45QiOjE2NTA2ODdfbg79.kompO-tqr-2wB3DEFS8D5qLvje8NsO8iHiQdssPJ_AS"
    }

    fun generateDummyListStories(): List<ListStoryItem> {
        val storyList: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..50) {
            val listStory = ListStoryItem(
                "$i",
                "name $i",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2022-02-22T22:22:22Z",
                "desc $i",
                100.0,
                100.0
            )
            storyList.add(listStory)
        }
        return storyList
    }

    fun generateDummyStories() : ListStoryResponse {
        return ListStoryResponse(generateDummyListStories(), false, "Success")
    }

    fun generateDummyNullStories(): List<ListStoryItem> {
        val storyList: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..50) {
            val listStory = ListStoryItem(
                null.toString(),
                null.toString(),
                null.toString(),
                null.toString(),
                null.toString(),
                null,
                null,
            )
            storyList.add(listStory)
        }
        return storyList
    }

    fun generateDummyAddStories() : AddNewResponse {
        return AddNewResponse(false, "success")
    }
}