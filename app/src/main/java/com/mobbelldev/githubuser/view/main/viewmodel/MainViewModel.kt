package com.mobbelldev.githubuser.view.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbelldev.githubuser.data.remote.response.ItemsItem
import com.mobbelldev.githubuser.data.remote.response.SearchUserResponse
import com.mobbelldev.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _searchUser = MutableLiveData<List<ItemsItem?>?>()
    val searchUser: LiveData<List<ItemsItem?>?> = _searchUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val query: String = "zenmobile"

    init {
        getSearchUsers(query)
    }

    fun getSearchUsers(q: String = query) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUser(q)
        client
            .enqueue(object : Callback<SearchUserResponse> {
                override fun onResponse(
                    call: Call<SearchUserResponse>,
                    response: Response<SearchUserResponse>
                ) {
                    _isLoading.value = false

                    val success = response.isSuccessful
                    val responseBody = response.body()
                    when (success && responseBody != null) {
                        true -> {
                            _searchUser.value = responseBody.items
                        }

                        else -> {
                            Log.e(TAG, "onFailure: ${response.message()}")
                        }
                    }
                }

                override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                    _isLoading.value = false

                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}