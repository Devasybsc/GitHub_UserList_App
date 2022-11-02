package com.example.githubuserlistapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

enum class LoadingStatus {
    LOADING, LOADED, ERROR
}

fun LiveData<LoadingStatus>.isLoading(): Boolean {
    return value == LoadingStatus.LOADING
}

fun MutableLiveData<LoadingStatus>.setLoading() {
    value = LoadingStatus.LOADING
}

fun MutableLiveData<LoadingStatus>.postLoading() {
    postValue(LoadingStatus.LOADING)
}

fun MutableLiveData<LoadingStatus>.setLoaded() {
    value = LoadingStatus.LOADED
}

fun MutableLiveData<LoadingStatus>.setError() {
    value = LoadingStatus.ERROR
}
