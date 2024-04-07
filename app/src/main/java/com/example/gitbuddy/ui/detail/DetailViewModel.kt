package com.example.gitbuddy.ui.detail

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

class DetailViewModel:ViewModel () {

    val detailUserResult = MutableLiveData<UserResult>()
    fun getDetailUser(username: String?) {
        viewModelScope.launch{
            flow{
                val response = ApiConfig
                    .getApiService()
                    .getDetailUserGithub("username")
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

}