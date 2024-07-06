package com.mobbelldev.githubuser.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobbelldev.githubuser.core.di.Injection
import com.mobbelldev.githubuser.data.repository.DetailUserRepository
import com.mobbelldev.githubuser.view.detail.viewmodel.DetailViewModel
import com.mobbelldev.githubuser.view.favorite.viewmodel.FavoriteViewModel
import com.mobbelldev.githubuser.view.theme.viewmodel.ThemeViewModel

class ViewModelFactory private constructor(
    private val detailUserRepository: DetailUserRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(detailUserRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(detailUserRepository) as T
        } else if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(detailUserRepository) as T
        }

        throw IllegalArgumentException("Unknown View Model class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context))
            }.also {
                INSTANCE = it
            }
    }
}