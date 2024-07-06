package com.mobbelldev.githubuser.view.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobbelldev.githubuser.data.local.entity.FavoriteEntity
import com.mobbelldev.githubuser.data.repository.DetailUserRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val detailUserRepository: DetailUserRepository) : ViewModel() {
    fun getDetailUser(username: String) = detailUserRepository.getDetailUser(username)

    fun getFavoriteUserByUsername(username: String) =
        detailUserRepository.getFavoriteUser(username)

    fun saveUser(user: FavoriteEntity) =
        viewModelScope.launch {
            detailUserRepository.saveUser(user)
        }

    fun deleteUser(user: FavoriteEntity) =
        viewModelScope.launch {
            detailUserRepository.deleteUser(user)
        }
}