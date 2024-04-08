package com.example.gitbuddy.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gitbuddy.R
import com.example.gitbuddy.data.remote.model.DetailUserResponse
import com.example.gitbuddy.databinding.ActivityDetailuserBinding
import com.example.gitbuddy.ui.adapter.DetailUserAdapter
import com.example.gitbuddy.ui.detail.follow.FollowFragment
import com.example.gitbuddy.utils.UserResult
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("DEPRECATION")
class DetailUserActivity :AppCompatActivity() {

    private val viewModel by viewModels<DetailViewModel>()
    private lateinit var binding : ActivityDetailuserBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityDetailuserBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val username = intent.getStringExtra("username") ?: ""

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
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}