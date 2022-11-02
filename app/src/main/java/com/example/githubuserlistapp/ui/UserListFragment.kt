package com.example.githubuserlistapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserlistapp.R
import com.example.githubuserlistapp.adapters.UserListAdapter
import com.example.githubuserlistapp.databinding.FragmentUserListBinding
import com.example.githubuserlistapp.model.LoadingStatus
import com.example.githubuserlistapp.model.UserShortInfo
import com.example.githubuserlistapp.viewmodels.UserListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class UserListFragment : DaggerFragment(), OnNavigateToUserDetailsListener {

    private lateinit var binding: FragmentUserListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: UserListViewModel by viewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        binding.lifecycleOwner = this

        val adapter = UserListAdapter(this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        binding.btRetry.setOnClickListener {
            viewModel.retryLoadPagedList()
        }

        viewModel.pagedList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToUserDetailsEvent.observe(viewLifecycleOwner, Observer {
            it?.let { userShortInfo ->
                this.findNavController().navigate(UserListFragmentDirections.toUserDetails(userShortInfo))
                viewModel.navigateToUserDetailsEnded()
            }
        })

        viewModel.pagedListLoadingStatus.observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                when (status) {
                    LoadingStatus.LOADING -> {
                        binding.loadingBar.visibility = View.VISIBLE
                    }
                    LoadingStatus.LOADED -> {
                        hideMissingNetworkLayout()
                    }
                    LoadingStatus.ERROR -> {
                        showMissingNetworkLayout()
                    }
                }
            }
        })

        return binding.root
    }

    private fun showMissingNetworkLayout() {
        binding.loadingBar.visibility = View.INVISIBLE
        binding.groupErrorWidget.visibility = View.VISIBLE
    }

    private fun hideMissingNetworkLayout() {
        binding.loadingBar.visibility = View.INVISIBLE
        binding.groupErrorWidget.visibility = View.GONE
    }

    override fun onNavigateToUserDetails(userShortInfo: UserShortInfo) {
        viewModel.navigateToUserDetails(userShortInfo)
    }
}

interface OnNavigateToUserDetailsListener {
    fun onNavigateToUserDetails(userShortInfo: UserShortInfo)
}
