package com.example.githubuserlistapp.di

import com.example.githubuserlistapp.network.GitHubUsersApi
import com.example.githubuserlistapp.repository.GitHubUserRepository
import com.example.githubuserlistapp.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(usersApi: GitHubUsersApi): UserRepository {
        return GitHubUserRepository(usersApi)
    }
}
