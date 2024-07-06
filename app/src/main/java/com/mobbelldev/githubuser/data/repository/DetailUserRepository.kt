package com.mobbelldev.githubuser.data.repository

import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mobbelldev.githubuser.core.util.Result
import com.mobbelldev.githubuser.core.util.SettingPreferences
import com.mobbelldev.githubuser.core.util.SettingPreferences.Companion.THEME_KEY
import com.mobbelldev.githubuser.data.local.entity.FavoriteEntity
import com.mobbelldev.githubuser.data.local.room.IFavoriteDao
import com.mobbelldev.githubuser.data.remote.response.DetailUserResponse
import com.mobbelldev.githubuser.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DetailUserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteDao: IFavoriteDao,
    private val preferences: SettingPreferences
) {

    fun getDetailUser(username: String): LiveData<Result<DetailUserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getThemeSetting(): Flow<Boolean> {
        return preferences.dataStore.data.map {
            it[THEME_KEY] ?: false
        }
    }

    suspend fun setThemeSetting(isDarkMode: Boolean) {
        preferences.dataStore.edit {
            it[THEME_KEY] = isDarkMode
        }
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteEntity>> {
        return favoriteDao.getAllUserByAsc()
    }

    fun getFavoriteUser(username: String): LiveData<FavoriteEntity> {
        return favoriteDao.getFavoriteByUsername(username)
    }

    suspend fun saveUser(user: FavoriteEntity) {
        return favoriteDao.insert(user)
    }

    suspend fun deleteUser(user: FavoriteEntity) {
        return favoriteDao.delete(user)
    }

    companion object {
        @Volatile
        private var INSTANCE: DetailUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: IFavoriteDao,
            preferences: SettingPreferences
        ): DetailUserRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DetailUserRepository(
                    apiService,
                    favoriteDao,
                    preferences
                )
            }.also {
                INSTANCE = it
            }
    }
}