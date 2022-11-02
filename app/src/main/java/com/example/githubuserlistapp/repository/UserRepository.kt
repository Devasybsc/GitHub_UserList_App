package com.example.githubuserlistapp.repository

import com.example.githubuserlistapp.model.User
import com.example.githubuserlistapp.model.UserShortInfo
import io.reactivex.Single

interface UserRepository {

    fun getUsersInfo(since: Long): Single<List<UserShortInfo>>

    fun getUser(login: String): Single<User>
}
