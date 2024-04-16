package com.example.gitbuddy.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitbuddy.data.remote.api.ApiConfig
import com.example.gitbuddy.utils.UserResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {

    val userResult = MutableLiveData<UserResult>()

    fun getGithubUser() {
        viewModelScope.launch{
            flow{
                val response = ApiConfig
                    .getApiService()
                    .getUserGithub()
                emit(response)
            }.onStart {
                userResult.value = UserResult.Loading(true)
            }.onCompletion {
                userResult.value = UserResult.Loading(false)
            }.catch {
                userResult.value = UserResult.Error(it)
                Log.e("error", it.toString())
                it.printStackTrace()
            }.collect{
                userResult.value = UserResult.Success(it)
            }
        }
    }
    fun getUser(username : String) {
        viewModelScope.launch{
            flow{
                val response = ApiConfig
                    .getApiService()
                    .searchUserGithub(username, 10)
                emit(response)
            }.onStart {
                userResult.value = UserResult.Loading(true)
            }.onCompletion {
                userResult.value = UserResult.Loading(false)
            }.catch {
                userResult.value = UserResult.Error(it)
                Log.e("error", it.toString())
                it.printStackTrace()
            }.collect{
                userResult.value = UserResult.Success(it.items)
            }
        }
    }
}