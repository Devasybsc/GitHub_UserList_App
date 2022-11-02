package com.example.githubuserlistapp.repository

import com.example.githubuserlistapp.model.User
import com.example.githubuserlistapp.model.UserShortInfo
import com.example.githubuserlistapp.network.GitHubUsersApi
import com.example.githubuserlistapp.network.model.asDomainModel
import io.reactivex.Single

class GitHubUserRepository(private val api: GitHubUsersApi) : UserRepository {

    override fun getUsersInfo(since: Long): Single<List<UserShortInfo>> {
        return api.getAllUsers(since).flatMap { response ->
            if (response.isSuccessful && response.body() != null) {
                Single.just(response.body()?.asDomainModel())
            } else {
                Single.error(Throwable(response.errorBody()?.string()))
            }
        }
    }

    override fun getUser(login: String): Single<User> {
        return api.getUser(login).flatMap { response ->
            if (response.isSuccessful && response.body() != null) {
                Single.just(response.body()?.asDomainModel())
            } else {
                Single.error(Throwable(response.errorBody()?.string()))
            }
        }
    }
}
