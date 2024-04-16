package com.example.gitbuddy.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity (tableName = "User")
@Parcelize
data class UserEntity(
    @PrimaryKey (autoGenerate = false)
    @ColumnInfo(name = "login")
    val login : String = "",

    @ColumnInfo(name = "avatar_url")
    val avatarUrl : String? = null,

    @ColumnInfo(name ="html_url")
    val htmlUrl: String? = null
) : Parcelable
