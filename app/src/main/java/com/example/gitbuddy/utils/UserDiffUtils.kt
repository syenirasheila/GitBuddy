package com.example.gitbuddy.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.gitbuddy.data.remote.model.ItemsItem

class UserDiffUtils(private val oldList: List<ItemsItem>, private val newList: List<ItemsItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id // Assuming id is unique

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}