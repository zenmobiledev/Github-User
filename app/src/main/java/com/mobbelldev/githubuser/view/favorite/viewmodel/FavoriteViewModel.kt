package com.mobbelldev.githubuser.view.favorite.viewmodel

import androidx.lifecycle.ViewModel
import com.mobbelldev.githubuser.data.repository.DetailUserRepository

class FavoriteViewModel(private val favoriteDetailRepository: DetailUserRepository) :
    ViewModel() {

    fun getAllFavoriteUser() =
        favoriteDetailRepository.getAllFavoriteUser()
}