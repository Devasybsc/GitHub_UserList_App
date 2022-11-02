package com.example.githubuserlistapp.adapters

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.example.githubuserlistapp.model.LoadingStatus
import com.example.githubuserlistapp.model.UserShortInfo
import com.example.githubuserlistapp.model.postLoading
import com.example.githubuserlistapp.model.setError
import com.example.githubuserlistapp.model.setLoaded
import com.example.githubuserlistapp.network.GITHUB_USER_INITIAL_KEY
import com.example.githubuserlistapp.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserListItemKeyedDataSource(
        private val userRepository: UserRepository,
        private val compositeDisposable: CompositeDisposable,
        private val pagedListLoadingStatus: MutableLiveData<LoadingStatus>
) : ItemKeyedDataSource<Long, UserShortInfo>() {

    private var initialParams: LoadInitialParams<Long>? = null
    private var initialCallback: LoadInitialCallback<UserShortInfo>? = null
    private var latestParams: LoadParams<Long>? = null
    private var latestCallback: LoadCallback<UserShortInfo>? = null

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<UserShortInfo>) {
        this.initialParams = params
        this.initialCallback = callback

        pagedListLoadingStatus.postLoading()
        userRepository.getUsersInfo(params.requestedInitialKey ?: GITHUB_USER_INITIAL_KEY)
                .doOnSubscribe { disposable ->
                    compositeDisposable.add(disposable)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { usersInfoList: List<UserShortInfo> ->
                            callback.onResult(usersInfoList)
                            pagedListLoadingStatus.setLoaded()
                        },
                        {
                            pagedListLoadingStatus.setError()
                        }
                )
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<UserShortInfo>) {
        this.latestParams = params
        this.latestCallback = callback

        pagedListLoadingStatus.postLoading()
        userRepository.getUsersInfo(params.key)
                .doOnSubscribe { disposable ->
                    compositeDisposable.add(disposable)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { usersInfoList: List<UserShortInfo> ->
                            callback.onResult(usersInfoList)
                            pagedListLoadingStatus.setLoaded()
                        },
                        {
                            pagedListLoadingStatus.setError()
                        }
                )
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<UserShortInfo>) {
    }

    override fun getKey(item: UserShortInfo): Long {
        return item.id
    }

    fun retryLastLoad() {
        when {
            isLoadInitialReady() -> {
                loadInitial(initialParams ?: return, initialCallback ?: return)
            }
            isLoadAfterReady() -> {
                loadAfter(latestParams ?: return, latestCallback ?: return)
            }
        }
    }

    private fun isLoadInitialReady(): Boolean {
        return initialParams != null && initialCallback != null && !isLoadAfterReady()
    }

    private fun isLoadAfterReady(): Boolean {
        return latestParams != null && latestCallback != null
    }
}
