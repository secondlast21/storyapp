package com.dicoding.storyapp.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var name: String? = null,
    var token: String? = null,
    var isLogin: Boolean = false
) : Parcelable