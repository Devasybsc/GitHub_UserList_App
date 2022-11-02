package com.example.githubuserlistapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuserlistapp.databinding.ListItemUserBinding
import com.example.githubuserlistapp.model.UserShortInfo
import com.example.githubuserlistapp.ui.OnNavigateToUserDetailsListener

class UserListAdapter(
        private val onNavigateToUserDetailsListener: OnNavigateToUserDetailsListener
) : PagedListAdapter<UserShortInfo, UserListAdapter.UserShortInfoViewHolder>(UserShortInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserShortInfoViewHolder {
        return UserShortInfoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserShortInfoViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onNavigateToUserDetailsListener)
        }
    }


    class UserShortInfoViewHolder private constructor(
            private val binding: ListItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userShortInfo: UserShortInfo, onNavigateToUserDetailsListener: OnNavigateToUserDetailsListener) {
            binding.userShortInfo = userShortInfo

            Glide.with(binding.avatarImageView)
                    .load(userShortInfo.avatarUrl)
                    .transform(CenterCrop(), RoundedCorners(12))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.avatarImageView)

            binding.rootLayout.setOnClickListener {
                onNavigateToUserDetailsListener.onNavigateToUserDetails(userShortInfo)
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): UserShortInfoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemUserBinding.inflate(layoutInflater, parent, false)
                return UserShortInfoViewHolder(binding)
            }
        }
    }
}

private class UserShortInfoDiffCallback : DiffUtil.ItemCallback<UserShortInfo>() {

    override fun areItemsTheSame(oldItem: UserShortInfo, newItem: UserShortInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserShortInfo, newItem: UserShortInfo): Boolean {
        return oldItem == newItem
    }
}
