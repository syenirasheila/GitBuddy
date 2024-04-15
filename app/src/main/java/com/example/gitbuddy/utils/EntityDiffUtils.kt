package com.example.gitbuddy.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.gitbuddy.data.local.entity.UserEntity

class EntityDiffUtils(private val oldList: List<UserEntity>, private val newList: List<UserEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList == newList

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}