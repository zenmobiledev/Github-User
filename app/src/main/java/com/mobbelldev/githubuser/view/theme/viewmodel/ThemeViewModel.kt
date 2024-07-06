package com.mobbelldev.githubuser.view.theme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mobbelldev.githubuser.data.repository.DetailUserRepository
import kotlinx.coroutines.launch

class ThemeViewModel(private val pref: DetailUserRepository) : ViewModel() {

    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun setThemeSetting(isState: Boolean) {
        viewModelScope.launch {
            pref.setThemeSetting(isState)
        }
    }
}