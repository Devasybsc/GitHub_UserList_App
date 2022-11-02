package com.example.githubuserlistapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserShortInfo(
        val id: Long,
        val login: String,
        val avatarUrl: String,
        val link: String
) : Parcelable
