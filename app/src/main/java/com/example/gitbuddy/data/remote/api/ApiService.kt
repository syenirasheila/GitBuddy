package com.example.gitbuddy.data.remote.api

import com.example.gitbuddy.BuildConfig
import com.example.gitbuddy.data.remote.model.DetailUserResponse
import com.example.gitbuddy.data.remote.model.GithubResponse
import com.example.gitbuddy.data.remote.model.ItemsItem
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUserGithub(
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ItemsItem>

    @GET("users/{username}")
    suspend fun getDetailUserGithub(
        @Path("username") username: String?,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): DetailUserResponse


    @GET("/users/{username}/followers")
    suspend fun getFollowersUserGithub(
        @Path("username") username: String?,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ItemsItem>


    @GET("/users/{username}/following")
    suspend fun getFollowingUserGithub(
        @Path("username") username: String?,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ItemsItem>


    @GET("search/users")
    suspend fun searchUserGithub(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): GithubResponse
}