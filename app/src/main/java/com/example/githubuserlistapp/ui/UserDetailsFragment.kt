package com.example.githubuserlistapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuserlistapp.R
import com.example.githubuserlistapp.databinding.FragmentUserDetailsBinding
import com.example.githubuserlistapp.model.LoadingStatus
import com.example.githubuserlistapp.model.User
import com.example.githubuserlistapp.viewmodels.UserDetailsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class UserDetailsFragment : DaggerFragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private val args: UserDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: UserDetailsViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.userShortInfo == null) {
            viewModel.userShortInfo = args.userShortInfo
            viewModel.loadUser()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        binding.lifecycleOwner = this

        binding.retryButton.setOnClickListener {
            viewModel.loadUser()
        }

        viewModel.userShortInfo?.let {
            loadAvatar(it.avatarUrl)
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let { user ->
                binding.user = user

                if (isNeedToUpdateAvatar(user)) {
                    loadAvatar(user.avatarUrl)
                }
            }
        })

        viewModel.userLoadingStatus.observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                when (status) {
                    LoadingStatus.LOADING -> {
                        binding.loadingBar.show()
                    }
                    LoadingStatus.LOADED -> {
                        binding.loadingBar.hide()
                        binding.retryButton.visibility = View.GONE
                    }
                    LoadingStatus.ERROR -> {
                        binding.loadingBar.hide()
                        binding.retryButton.visibility = View.VISIBLE
                    }
                }
            }
        })

        return binding.root
    }

    private fun isNeedToUpdateAvatar(user: User): Boolean {
        return user.avatarUrl != viewModel.userShortInfo?.avatarUrl || binding.avatarImageView.drawable == null
    }

    private fun loadAvatar(avatarUrl: String) {
        Glide.with(binding.avatarImageView)
            .load(avatarUrl)
            .transform(CenterCrop())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.avatarImageView)
    }
}
