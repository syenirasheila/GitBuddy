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
import com.example.gitbuddy.utils.UserDiffUtils

class ListUserAdapter (private val listener:(ItemsItem) -> Unit ):

    RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {

    private val listUser : MutableList<ItemsItem> = mutableListOf()

        fun setData(newList: MutableList<ItemsItem>) {
            val diffCallback = UserDiffUtils(listUser, newList)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            listUser.clear()
            listUser.addAll(newList)
            diffResult.dispatchUpdatesTo(this)
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
