package com.example.githubuserlistapp.network.model

import com.example.githubuserlistapp.model.UserShortInfo
import com.squareup.moshi.Json

data class GitHubUserShortInfo(
    val id: Long,
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "html_url")
    val link: String
)

fun GitHubUserShortInfo.asDomainModel(): UserShortInfo {
    return UserShortInfo(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        link = link
    )
}

fun List<GitHubUserShortInfo>.asDomainModel(): List<UserShortInfo> {
    return map {
        it.asDomainModel()
    }
}
