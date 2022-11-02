package com.example.githubuserlistapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.githubuserlistapp.adapters.UserListDataSourceFactory
import com.example.githubuserlistapp.model.LoadingStatus
import com.example.githubuserlistapp.model.UserShortInfo
import com.example.githubuserlistapp.model.isLoading
import com.example.githubuserlistapp.network.GITHUB_USER_INITIAL_KEY
import com.example.githubuserlistapp.network.GITHUB_USER_PAGE_SIZE
import com.example.githubuserlistapp.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    userRepository: UserRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private val dataSourceFactory: UserListDataSourceFactory

    val pagedList: LiveData<PagedList<UserShortInfo>>

    private val _pagedListLoadingStatus = MutableLiveData<LoadingStatus>()
    val pagedListLoadingStatus: LiveData<LoadingStatus> = _pagedListLoadingStatus

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(GITHUB_USER_PAGE_SIZE)
            .setPageSize(GITHUB_USER_PAGE_SIZE)
            .build()

        dataSourceFactory = UserListDataSourceFactory(userRepository, compositeDisposable, _pagedListLoadingStatus)
        pagedList = LivePagedListBuilder(dataSourceFactory, config)
            .setInitialLoadKey(GITHUB_USER_INITIAL_KEY)
            .build()
    }

    fun retryLoadPagedList() {
        if (pagedListLoadingStatus.isLoading()) return

        dataSourceFactory.dataSource.retryLastLoad()
    }

    //region navigateToUserDetailsEvent
    private val _navigateToUserDetailsEvent = MutableLiveData<UserShortInfo>()
    val navigateToUserDetailsEvent: LiveData<UserShortInfo> = _navigateToUserDetailsEvent

    fun navigateToUserDetails(userShortInfo: UserShortInfo) {
        _navigateToUserDetailsEvent.value = userShortInfo
    }

    fun navigateToUserDetailsEnded() {
        _navigateToUserDetailsEvent.value = null
    }
    //endregion

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
