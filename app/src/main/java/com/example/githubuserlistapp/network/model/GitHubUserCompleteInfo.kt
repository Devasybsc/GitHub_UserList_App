package com.example.githubuserlistapp.network.model

import com.example.githubuserlistapp.model.User
import com.squareup.moshi.Json

data class GitHubUserCompleteInfo(

        val id: Long,

        val login: String,

        @Json(name = "avatar_url")
        val avatarUrl: String,

        val name: String?,

        val location: String?,

        @Json(name = "html_url")
        val link: String
)

fun GitHubUserCompleteInfo.asDomainModel(): User {
    return User(
            id = id,
            login = login,
            avatarUrl = avatarUrl,
            name = name ?: "",
            location = location ?: "",
            link = link
    )
}
