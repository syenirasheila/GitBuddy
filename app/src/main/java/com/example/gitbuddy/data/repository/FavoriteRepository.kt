package com.example.gitbuddy.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.gitbuddy.data.local.entity.UserEntity
import com.example.gitbuddy.data.local.room.FavoriteDao
import com.example.gitbuddy.data.local.room.FavoriteRoomDb
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository (application: Application) {
    private val mFavoriteDao : FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDb.getDatabase(application)
        mFavoriteDao = db.favoritedDao()
    }

    fun getAllFavoritedUser(): LiveData<List<UserEntity>> = mFavoriteDao.getAllFavoritedUser()

    fun getFavoritedByUsername(username : String): LiveData<UserEntity> = mFavoriteDao.getFavoritedByUsername(username)

    fun insert (user: UserEntity) {
        executorService.execute { mFavoriteDao.insert(user) }
    }

    fun delete (user: UserEntity) {
        executorService.execute { mFavoriteDao.delete(user) }
    }
}