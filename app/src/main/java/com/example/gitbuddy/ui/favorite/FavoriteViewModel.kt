package com.example.gitbuddy.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.example.gitbuddy.data.local.entity.UserEntity
import com.example.gitbuddy.data.repository.FavoriteRepository
import com.example.gitbuddy.utils.EntityDiffUtils


class FavoriteViewModel (mApplication: Application): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mFavoriteRepository : FavoriteRepository = FavoriteRepository(mApplication)
    fun getAllFavoritedUser() : LiveData<List<UserEntity>> {
        _isLoading.value = false
        return mFavoriteRepository.getAllFavoritedUser()
    }
}