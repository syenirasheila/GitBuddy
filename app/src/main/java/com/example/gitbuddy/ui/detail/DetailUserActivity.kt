package com.example.gitbuddy.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gitbuddy.R
import com.example.gitbuddy.data.remote.model.DetailUserResponse
import com.example.gitbuddy.databinding.ActivityDetailuserBinding
import com.example.gitbuddy.utils.UserResult

class DetailUserActivity :AppCompatActivity() {

    private val viewModel by viewModels<DetailViewModel>()
    private lateinit var binding:ActivityDetailuserBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityDetailuserBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val username = intent.getStringExtra("username")

        viewModel.detailUserResult.observe(this){ it ->
            when(it) {
                is UserResult.Success<*> -> {
                    val user = it.data as DetailUserResponse
                    Glide.with(this@DetailUserActivity)
                        .load(user.avatarUrl)
                        .apply(
                            RequestOptions
                                .circleCropTransform()
                                .placeholder(R.drawable.logo_gitbuddy)
                                .error(R.drawable.logo_gitbuddy)
                        ).into(binding.ivAvatar)

                    binding.tvName.text = user.name
                    binding.tvUsername.text = user.login
                    binding.tvLocation.text = user.location.toString()
                    binding.tvCompany.text = user.company.toString()
                    binding.tvFollowers.text =  user.followers.toString()
                    binding.tvRepository.text = user.publicRepos.toString()
                    binding.tvFollowing.text = user.following.toString()

//                    val followersNumber = user.followers
//                    if (followersNumber != null) {
//                        if (followersNumber > 10000) {
//                            "${followersNumber / 1000}.${(followersNumber % 1000) / 100}K".also {
//                                binding.tvFollowers.text = it
//                            }
//                        } else {
//                            binding.tvFollowers.text = user.followers.toString()
//                        }
//                    }
//
//                    val followingNumber = user.followers
//                    if (followingNumber != null) {
//                        if (followingNumber > 10000) {
//                            "${followingNumber / 1000}.${(followingNumber % 1000) / 100}K".also {
//                                binding.tvFollowing.text = it
//                            }
//                        } else {
//                            binding.tvFollowing.text = user.following.toString()
//                        }
//                    }
//
//                    val repoNumber = user.followers
//                    if (repoNumber != null) {
//                        if (repoNumber > 10000) {
//                            "${repoNumber / 1000}.${(repoNumber % 1000) / 100}K".also {
//                                binding.tvRepository.text = it
//                            }
//                        } else {
//                            binding.tvRepository.text = user.publicRepos.toString()
//                        }
//                    }

                }
                is UserResult.Error -> {
                    Toast.makeText(this,it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is UserResult.Loading -> {
                    binding.progressBarDetail.isVisible = it.isLoading
                }
            }
        }
        viewModel.getDetailUser(username)
    }
}