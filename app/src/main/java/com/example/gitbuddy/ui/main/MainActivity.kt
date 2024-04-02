package com.example.gitbuddy.ui.main

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitbuddy.adapter.ListUserAdapter
import com.example.gitbuddy.data.remote.model.ItemsItem
import com.example.gitbuddy.databinding.ActivityMainBinding
import com.example.gitbuddy.ui.splashscreen.SplashScreenViewModel
import com.example.gitbuddy.utils.UserResult

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashScreenViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        ListUserAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                !viewModel.isReady.value
            }
            setOnExitAnimationListener{ screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }

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
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
        mainViewModel.getGithubUser()

    }
}