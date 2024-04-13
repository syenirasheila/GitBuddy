package com.example.gitbuddy.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gitbuddy.R
import com.example.gitbuddy.data.remote.model.ItemsItem
import com.example.gitbuddy.databinding.UserCardBinding

class ListUserAdapter (

    private val listener:(ItemsItem) -> Unit ):

    RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {

    private val listUser : MutableList<ItemsItem> = mutableListOf()

//    fun setData(listUser : MutableList<ItemsItem>){
//        this.listUser.clear()
//        this.listUser.addAll(listUser)
//        notifyDataSetChanged()
//    }

        fun setData(newList: MutableList<ItemsItem>) {
            val diffCallback = UserDiffCallback(listUser, newList)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            listUser.clear()
            listUser.addAll(newList)
            diffResult.dispatchUpdatesTo(this)
        }

        class UserDiffCallback(private val oldList: List<ItemsItem>, private val newList: List<ItemsItem>) : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldList.size
            override fun getNewListSize(): Int = newList.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition].id == newList[newItemPosition].id // Assuming id is unique

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition] == newList[newItemPosition]
        }

        class UserViewHolder(private val binding: UserCardBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(user: ItemsItem) {
                binding.tvItemUsername.text = user.login
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(
                        RequestOptions
                            .circleCropTransform()
                            .placeholder(R.drawable.logo_gitbuddy)
                            .error(R.drawable.logo_gitbuddy)
                    ).into(binding.ivItemAvatar)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val userBinding =
                UserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UserViewHolder(userBinding)
        }

        override fun getItemCount(): Int = listUser.size

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val item = listUser[position]
            holder.bind(item)
            holder.itemView.setOnClickListener {
                listener(item)
            }
        }
    }
