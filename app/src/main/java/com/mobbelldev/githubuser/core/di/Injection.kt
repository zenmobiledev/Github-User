package com.mobbelldev.githubuser.core.di

import android.content.Context
import com.mobbelldev.githubuser.core.util.SettingPreferences
import com.mobbelldev.githubuser.core.util.dataStore
import com.mobbelldev.githubuser.data.local.room.FavoriteRoomDatabase
import com.mobbelldev.githubuser.data.remote.retrofit.ApiConfig
import com.mobbelldev.githubuser.data.repository.DetailUserRepository

object Injection {
    fun provideRepository(context: Context): DetailUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteRoomDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val pref = SettingPreferences.getInstance(context.dataStore)
        return DetailUserRepository.getInstance(apiService, dao, pref)
    }
}