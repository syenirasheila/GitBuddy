package com.example.gitbuddy.utils

sealed class UserResult {
    data class Success<out T>(val data: T) : UserResult()
    data class Error(val exception: Throwable) : UserResult()
    data class Loading(val isLoading:Boolean) : UserResult()
}