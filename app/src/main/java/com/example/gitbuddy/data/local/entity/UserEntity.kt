package com.example.gitbuddy.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity (tableName = "User")
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    var login : String = "",

    @ColumnInfo(name = "avatar_url")
    var avatarUrl : String? = null
) : Parcelable
