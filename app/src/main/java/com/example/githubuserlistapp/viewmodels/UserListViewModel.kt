package com.example.githubuserlistapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {
}
