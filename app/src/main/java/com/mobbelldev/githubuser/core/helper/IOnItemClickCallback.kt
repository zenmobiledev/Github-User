package com.mobbelldev.githubuser.core.helper

import com.mobbelldev.githubuser.data.remote.response.ItemsItem

interface IOnItemClickCallback {
    fun onItemClicked(data: ItemsItem)
}