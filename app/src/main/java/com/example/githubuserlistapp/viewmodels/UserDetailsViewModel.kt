package com.example.githubuserlistapp.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserlistapp.model.LoadingStatus
import com.example.githubuserlistapp.model.User
import com.example.githubuserlistapp.model.UserShortInfo
import com.example.githubuserlistapp.model.isLoading
import com.example.githubuserlistapp.model.setError
import com.example.githubuserlistapp.model.setLoaded
import com.example.githubuserlistapp.model.setLoading
import com.example.githubuserlistapp.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    var userShortInfo: UserShortInfo? = null

    private val _userLoadingStatus = MutableLiveData<LoadingStatus>()
    val userLoadingStatus: LiveData<LoadingStatus> = _userLoadingStatus

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private fun setNewUser(user: User) {
        _user.value = user
    }

    @SuppressLint("CheckResult")
    fun loadUser() {
        if (userLoadingStatus.isLoading()) return

        _userLoadingStatus.setLoading()
        userRepository.getUser(userShortInfo?.login ?: "")
                .doOnSubscribe { disposable ->
                    compositeDisposable.add(disposable)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user: User ->
                            setNewUser(user)
                            _userLoadingStatus.setLoaded()
                        },
                        {
                            _userLoadingStatus.setError()
                        }
                )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
