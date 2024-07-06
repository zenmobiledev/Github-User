package com.mobbelldev.githubuser.view.detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbelldev.githubuser.data.remote.response.ItemsItem
import com.mobbelldev.githubuser.data.remote.retrofit.ApiConfig
import com.mobbelldev.githubuser.view.detail.FollowerAndFollowingFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersAndFollowingViewModel : ViewModel() {
    private val _followers = MutableLiveData<List<ItemsItem?>?>()
    val followers: LiveData<List<ItemsItem?>?> = _followers

    private val _following = MutableLiveData<List<ItemsItem?>?>()
    val following: LiveData<List<ItemsItem?>?> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowers(username)
        client
            .enqueue(object : Callback<List<ItemsItem?>?> {
                override fun onResponse(
                    call: Call<List<ItemsItem?>?>,
                    response: Response<List<ItemsItem?>?>
                ) {
                    _isLoading.value = false

                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _followers.postValue(responseBody)
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem?>?>, t: Throwable) {
                    _isLoading.value = false

                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowing(username)
        client
            .enqueue(object : Callback<List<ItemsItem?>?> {
                override fun onResponse(
                    call: Call<List<ItemsItem?>?>,
                    response: Response<List<ItemsItem?>?>
                ) {
                    _isLoading.value = false

                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _following.postValue(responseBody)
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem?>?>, t: Throwable) {
                    _isLoading.value = false

                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    companion object {
        private val TAG = FollowerAndFollowingFragment::class.java.simpleName
    }
}