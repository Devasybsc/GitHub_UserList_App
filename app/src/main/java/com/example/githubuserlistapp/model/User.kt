package com.example.githubuserlistapp.model

data class User(
        val id: Long,
        val login: String,
        val avatarUrl: String,
        val name: String,
        val location: String,
        val link: String
)
