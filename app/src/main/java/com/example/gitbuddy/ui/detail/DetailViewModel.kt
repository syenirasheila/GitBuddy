package com.example.gitbuddy.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitbuddy.data.local.entity.UserEntity
import com.example.gitbuddy.data.remote.api.ApiConfig
import com.example.gitbuddy.data.repository.FavoriteRepository
import com.example.gitbuddy.utils.UserResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel (application: Application) :ViewModel () {

    val detailUserResult = MutableLiveData<UserResult>()
    val followerUserResult = MutableLiveData<UserResult>()
    val followingUserResult= MutableLiveData<UserResult>()
    private val mFavoriteUserRepository : FavoriteRepository = FavoriteRepository(application)
//    val favoriteResultSuccess = MutableLiveData<Boolean>()
//    val favoriteResultDelete = MutableLiveData<Boolean>()
//    private var isFavorite = false

    fun getDetailUser(username: String?) {
        viewModelScope.launch{
            flow{
                val response = ApiConfig
                    .getApiService()
                    .getDetailUserGithub(username)
                emit(response)
            }.onStart {
                detailUserResult.value = UserResult.Loading(true)
            }.onCompletion {
                detailUserResult.value = UserResult.Loading(false)
            }.catch {
                detailUserResult.value = UserResult.Error(it)
                Log.e("error", it.toString())
                it.printStackTrace()
            }.collect{
                detailUserResult.value = UserResult.Success(it)
            }
        }
    }

    fun getFollowers(username: String?){
        viewModelScope.launch{
            flow{
                val response = ApiConfig
                    .getApiService()
                    .getFollowersUserGithub(username)
                emit(response)
            }.onStart {
                followerUserResult.value = UserResult.Loading(true)
            }.onCompletion {
                followerUserResult.value = UserResult.Loading(false)
            }.catch {
                followerUserResult.value = UserResult.Error(it)
                Log.e("error", it.toString())
                it.printStackTrace()
            }.collect{
                followerUserResult.value = UserResult.Success(it)
            }
        }
    }

    fun getFollowing(username: String?){
        viewModelScope.launch{
            flow{
                val response = ApiConfig
                    .getApiService()
                    .getFollowingUserGithub(username)
                emit(response)
            }.onStart {
                followingUserResult.value = UserResult.Loading(true)
            }.onCompletion {
                followingUserResult.value = UserResult.Loading(false)
            }.catch {
                followingUserResult.value = UserResult.Error(it)
                Log.e("error", it.toString())
                it.printStackTrace()
            }.collect{
                followingUserResult.value = UserResult.Success(it)
            }
        }
    }

    fun insertFavoritedUser(user: UserEntity) {
        mFavoriteUserRepository.insert(user)
    }

    fun deleteFavoritedUser(user: UserEntity) {
        mFavoriteUserRepository.delete(user)
    }

    fun getFavoritedByUsername(username: String): LiveData<UserEntity> = mFavoriteUserRepository.getFavoritedByUsername(username)

//    fun setFavorited(user: UserEntity?) {
//        viewModelScope.launch {
//            user?.let {
//                if (isFavorite) {
//                    deleteFavoritedUser(user)
//                    favoriteResultDelete.value = true
//                } else {
//                    insertFavoritedUser(user)
//                    favoriteResultSuccess.value = true
//                }
//            }
//            isFavorite = !isFavorite
//        }
//    }
//
//    fun getFavorited(username: String, listenFavorite: () -> Unit){
//        viewModelScope.launch {
//            val user = getFavoritedByUsername(username)
//            user.let {
//                listenFavorite()
//                isFavorite = true
//            }
//        }
//    }

}