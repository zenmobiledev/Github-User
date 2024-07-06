package com.mobbelldev.githubuser.core.helper

import android.view.View
import android.widget.ProgressBar

object ShowLoading {
    fun showLoading(progressBar: ProgressBar, state: Boolean) {
        progressBar.apply {
            visibility = when (state) {
                true -> View.VISIBLE
                false -> View.GONE
            }
        }
    }
}