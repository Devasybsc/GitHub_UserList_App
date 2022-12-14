package com.example.githubuserlistapp.network

import com.example.githubuserlistapp.network.model.GitHubUserCompleteInfo
import com.example.githubuserlistapp.network.model.GitHubUserShortInfo
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val GITHUB_BASE_URL = "https://api.github.com/"
const val GITHUB_USER_INITIAL_KEY = 0L
const val GITHUB_USER_PAGE_SIZE = 30

interface GitHubUsersApi {

    @GET("/users")
    fun getAllUsers(@Query("since") since: Long): Single<Response<List<GitHubUserShortInfo>>>

    @GET("/users/{login}")
    fun getUser(@Path("login") login: String): Single<Response<GitHubUserCompleteInfo>>
}
