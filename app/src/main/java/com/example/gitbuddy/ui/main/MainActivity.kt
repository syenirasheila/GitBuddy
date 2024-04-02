package com.example.gitbuddy.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitbuddy.adapter.ListUserAdapter
import com.example.gitbuddy.data.remote.api.ApiConfig
import com.example.gitbuddy.databinding.ActivityMainBinding
import com.example.gitbuddy.ui.splashscreen.SplashScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        ListUserAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel by viewModels<SplashScreenViewModel>()

        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                !viewModel.isReady.value
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.adapter = adapter

        GlobalScope.launch ( Dispatchers.IO ) {
            flow{
                val response = ApiConfig
            }
        }
    }
}