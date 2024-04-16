package com.example.gitbuddy.ui.favorite


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitbuddy.databinding.ActivityFavoriteBinding
import com.example.gitbuddy.ui.detail.DetailUserActivity
import com.example.gitbuddy.utils.ViewModelFactory

@Suppress("DEPRECATION")
class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val adapter by lazy {
        FavoriteAdapter { user ->
            Intent(this, DetailUserActivity::class.java).apply{
                putExtra("username", user.login)
                putExtra("avatarUrl",user.avatarUrl)
                putExtra("userUrl",user.htmlUrl)
                startActivity(this)
            }
        }
    }
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvFavoriteUsers.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUsers.adapter = adapter

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        favoriteViewModel.getAllFavoritedUser().observe(this) {
            val isListEmpty = it.isEmpty()
            if(isListEmpty){
                showNoFavorite(true)
            } else {
                showNoFavorite(false)
                adapter.setDataEntity(it)

            }
        }
        favoriteViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFavorite.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showNoFavorite(isDataNotFound: Boolean) {
        binding.apply {
            if (isDataNotFound) {
                rvFavoriteUsers.visibility = View.GONE
                ivNofavorite.visibility = View.VISIBLE
            } else {
                rvFavoriteUsers.visibility = View.VISIBLE
                ivNofavorite.visibility = View.GONE
            }
        }
    }

}