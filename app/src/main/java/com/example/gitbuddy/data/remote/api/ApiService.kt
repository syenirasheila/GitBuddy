package com.example.gitbuddy.data.remote.api

import com.example.gitbuddy.BuildConfig
import com.example.gitbuddy.data.remote.model.DetailUserResponse
import com.example.gitbuddy.data.remote.model.GithubResponse
import com.example.gitbuddy.data.remote.model.ItemsItem
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap


interface ApiService {

    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUserGithub(
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ItemsItem>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUserGithub(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): DetailUserResponse

    @JvmSuppressWildcards
    @GET("/users/{username}/followers")
    suspend fun getFollowersUserGithub(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ItemsItem>

    @JvmSuppressWildcards
    @GET("/users/{username}/following")
    suspend fun getFollowingUserGithub(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ItemsItem>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun searchUserGithub(
        @QueryMap params: Map<String, Any>,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): GithubResponse
}