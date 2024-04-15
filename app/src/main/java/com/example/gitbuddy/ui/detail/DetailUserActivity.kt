package com.example.gitbuddy.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gitbuddy.R
import com.example.gitbuddy.data.local.entity.UserEntity
import com.example.gitbuddy.data.remote.model.DetailUserResponse
import com.example.gitbuddy.databinding.ActivityDetailuserBinding
import com.example.gitbuddy.ui.detail.follow.FollowFragment
import com.example.gitbuddy.utils.UserResult
import com.example.gitbuddy.utils.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("DEPRECATION")
class DetailUserActivity :AppCompatActivity() {

    private lateinit var binding : ActivityDetailuserBinding
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityDetailuserBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val username = intent.getStringExtra("username") ?: ""
        val avatarUrl = intent.getStringExtra("avatarUrl")

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.detailUserResult.observe(this){
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
                    val userLocation = user.location ?: "-"
                    binding.tvLocation.text = userLocation.toString()
                    binding.tvFollowers.text =  user.followers.toString()
                    binding.tvRepository.text = user.publicRepos.toString()
                    binding.tvFollowing.text = user.following.toString()

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

        val fragments = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWING)
        )

        val titleFragments = mutableListOf(
            getString(R.string.followers), getString(R.string.following)
        )

        val adapter = DetailUserAdapter(this,fragments)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tabLayout, positions ->
            tabLayout.text = titleFragments[positions]
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                   viewModel.getFollowers(username)
                } else {
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    viewModel.getFollowers(username)
                } else {
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    viewModel.getFollowers(username)
                } else {
                    viewModel.getFollowing(username)
                }
            }

        })

        viewModel.getFollowers(username)

        viewModel.getFavoritedByUsername(username).observe(this) { user ->
            if (user == null) {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(binding.fabFavorite.context, R.drawable.ic_favorite_border))
                binding.fabFavorite.setOnClickListener {
                    viewModel.insertFavoritedUser(UserEntity(username,avatarUrl))
                }
            } else {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(binding.fabFavorite.context, R.drawable.ic_favorited))
                binding.fabFavorite.setOnClickListener {
                    viewModel.deleteFavoritedUser(user)
                }
            }
        }
//        val user = intent.getParcelableExtra<UserEntity>("username")
//
//        binding.fabFavorite.setOnClickListener {
//            viewModel.setFavorited(user)
//
//        }
//
//        viewModel.getFavorited(username){
//            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(binding.fabFavorite.context, R.drawable.ic_favorite_border))
//        }
//
//        viewModel.favoriteResultSuccess.observe(this) {
//            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(binding.fabFavorite.context, R.drawable.ic_favorited))
//        }
//
//        viewModel.favoriteResultDelete.observe(this) {
//            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(binding.fabFavorite.context, R.drawable.ic_favorite_border))
//        }
    }
}