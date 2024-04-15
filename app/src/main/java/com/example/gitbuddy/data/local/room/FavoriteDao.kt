package com.example.gitbuddy.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitbuddy.data.local.entity.UserEntity

@Dao
interface FavoriteDao {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity)

    @Delete
    fun delete(user:UserEntity)

    @Query("SELECT * FROM user WHERE login = :username")
    fun getFavoritedByUsername(username: String): LiveData<UserEntity>

    @Query("SELECT * from user ORDER BY login ASC")
    fun getAllFavoritedUser(): LiveData<List<UserEntity>>

}