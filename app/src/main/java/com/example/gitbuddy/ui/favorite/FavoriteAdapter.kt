package com.example.gitbuddy.ui.favorite

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gitbuddy.R
import com.example.gitbuddy.data.local.entity.UserEntity
import com.example.gitbuddy.databinding.UserCardBinding
import com.example.gitbuddy.utils.EntityDiffUtils

class FavoriteAdapter (private val listener: (UserEntity) -> Unit):

    RecyclerView.Adapter<FavoriteAdapter.UserViewHolder>() {

    private var userList = emptyList<UserEntity>()

    fun setDataEntity(newList:  List<UserEntity>) {
        val diffCallback = EntityDiffUtils(userList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.userList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    class UserViewHolder(private val binding: UserCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
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

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = userList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }
}
