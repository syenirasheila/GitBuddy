package com.example.gitbuddy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gitbuddy.R
import com.example.gitbuddy.data.remote.model.ItemsItem
import com.example.gitbuddy.databinding.UserCardBinding

class ListUserAdapter (
    private val listUser : MutableList<ItemsItem> = mutableListOf(),
    private val listener:(ItemsItem) -> Unit ):

    RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listUser : MutableList<ItemsItem>){
        this.listUser.clear()
        this.listUser.addAll(listUser)
        notifyDataSetChanged()
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
        val userBinding = UserCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(userBinding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = listUser[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            listener(item)
        }
    }
}
