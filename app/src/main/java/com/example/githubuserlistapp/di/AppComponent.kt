package com.example.githubuserlistapp.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ViewModelModule::class
])
interface AppComponent : AndroidInjector<GitHubUserListApplication> {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }
}