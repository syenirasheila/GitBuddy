package com.example.gitbuddy.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gitbuddy.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class FavoriteRoomDb : RoomDatabase() {
    abstract fun favoritedDao() : FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE : FavoriteRoomDb? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteRoomDb {
            if (INSTANCE == null) {
                synchronized(FavoriteRoomDb::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteRoomDb::class.java, "favorited_db")
                        .build()
                }
            }
            return INSTANCE as FavoriteRoomDb
        }
    }
}