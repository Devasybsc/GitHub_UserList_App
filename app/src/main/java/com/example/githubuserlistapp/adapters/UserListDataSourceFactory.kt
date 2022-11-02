package com.example.githubuserlistapp.adapters

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.githubuserlistapp.model.LoadingStatus
import com.example.githubuserlistapp.model.UserShortInfo
import com.example.githubuserlistapp.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable


class UserListDataSourceFactory(
        private val userRepository: UserRepository,
        private val compositeDisposable: CompositeDisposable,
        private val pagedListLoadingStatus: MutableLiveData<LoadingStatus>
) : DataSource.Factory<Long, UserShortInfo>() {

    lateinit var dataSource: UserListItemKeyedDataSource
        private set

    override fun create(): DataSource<Long, UserShortInfo> {
        dataSource = UserListItemKeyedDataSource(userRepository, compositeDisposable, pagedListLoadingStatus)
        return dataSource
    }
}
