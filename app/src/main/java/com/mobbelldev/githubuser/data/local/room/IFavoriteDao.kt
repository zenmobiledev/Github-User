package com.mobbelldev.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mobbelldev.githubuser.data.local.entity.FavoriteEntity

@Dao
interface IFavoriteDao {

    @Insert
    suspend fun insert(favorite: FavoriteEntity)

    @Delete
    suspend fun delete(favorite: FavoriteEntity)

    @Query("SELECT * FROM FavoriteEntity WHERE username = :username")
    fun getFavoriteByUsername(username: String): LiveData<FavoriteEntity>

    @Query("SELECT * FROM FavoriteEntity ORDER BY username ASC")
    fun getAllUserByAsc(): LiveData<List<FavoriteEntity>>

}