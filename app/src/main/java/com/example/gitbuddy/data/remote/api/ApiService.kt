package com.example.gitbuddy.data.remote.api

import com.example.gitbuddy.BuildConfig
import com.example.gitbuddy.data.remote.model.DetailUserResponse
import com.example.gitbuddy.data.remote.model.GithubResponse
import com.example.gitbuddy.data.remote.model.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: token ${BuildConfig.TOKEN}", "UserResponse-Agent: request")
    @GET("search/user")
    suspend fun searchGithubUser(@Query("q") q:String): Call<GithubResponse>

    @Headers("Authorization: token ${BuildConfig.TOKEN}", "UserResponse-Agent: request")
    @GET("users")
    suspend fun userList(): GithubResponse

    @Headers("Authorization: token ${BuildConfig.TOKEN}", "UserResponse-Agent: request")
    @GET("users/{username}")
    suspend fun detailGithubUser(@Path("username") username:String): Call<DetailUserResponse>

    @Headers("Authorization: token ${BuildConfig.TOKEN}", "UserResponse-Agent: request")
    @GET("user/{username}/followers")
    suspend fun getFollowerList(@Path("username") username: String): Call<List<ItemsItem>>

    @Headers("Authorization: token ${BuildConfig.TOKEN}", "UserResponse-Agent: request")
    @GET("user/{username}/following")
    suspend fun getFollowingList(@Path("username") username: String): Call<List<ItemsItem>>
}