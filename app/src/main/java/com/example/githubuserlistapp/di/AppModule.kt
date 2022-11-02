package com.example.githubuserlistapp.di

import com.example.githubuserlistapp.MainActivity
import com.example.githubuserlistapp.ui.UserDetailsFragment
import com.example.githubuserlistapp.ui.UserListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class AppModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindUserListFragment(): UserListFragment

    @ContributesAndroidInjector
    abstract fun bindUserDetailsFragment(): UserDetailsFragment
}
