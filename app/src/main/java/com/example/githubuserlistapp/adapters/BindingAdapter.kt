package com.example.githubuserlistapp.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("nickname")
fun TextView.setNickName(nickname: String?) {
    text = nickname?.let { "@$it" }
}

@BindingAdapter("visibilityByText")
fun TextView.setVisibilityByText(text: String?) {
    visibility = if (text.isNullOrBlank()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}
