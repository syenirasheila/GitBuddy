package com.example.gitbuddy.ui.main


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitbuddy.ui.adapter.ListUserAdapter
import com.example.gitbuddy.data.remote.model.ItemsItem
import com.example.gitbuddy.databinding.ActivityMainBinding
import com.example.gitbuddy.ui.detail.DetailUserActivity
import com.example.gitbuddy.utils.UserResult

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        ListUserAdapter{ user ->
            Intent(this,DetailUserActivity::class.java).apply{
                putExtra("username", user.login)
                startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.adapter = adapter

        mainViewModel.userResult.observe(this){
            when(it) {
                is UserResult.Success<*> -> {
                    adapter.setData(it.data as MutableList<ItemsItem>)
                }
                is UserResult.Error -> {
                    Toast.makeText(this,it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is UserResult.Loading -> {
                    binding.progressBarMain.isVisible = it.isLoading
                }
            }
        }
        mainViewModel.getGithubUser()

    }
}