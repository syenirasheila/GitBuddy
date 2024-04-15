package com.example.gitbuddy.ui.main


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitbuddy.data.local.preferences.SettingPreferences
import com.example.gitbuddy.data.remote.model.ItemsItem
import com.example.gitbuddy.databinding.ActivityMainBinding
import com.example.gitbuddy.ui.detail.DetailUserActivity
import com.example.gitbuddy.ui.favorite.FavoriteActivity
import com.example.gitbuddy.utils.SettingViewModelFactory
import com.example.gitbuddy.utils.UserResult

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        ListUserAdapter{ user ->
            Intent(this,DetailUserActivity::class.java).apply{
                putExtra("username", user.login)
                putExtra("avatarUrl",user.avatarUrl)
                putExtra("userUrl",user.htmlUrl)
                startActivity(this)
            }
        }
    }
    private val settingViewModel by viewModels<SettingViewModel> {
        SettingViewModelFactory(SettingPreferences.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.adapter = adapter


        binding.btnFavorite.setOnClickListener{
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        binding.svUser.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val userQuery = query.toString()
                binding.svUser.clearFocus()
                mainViewModel.getUser(userQuery)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })


        mainViewModel.userResult.observe(this) {
            when(it) {
                is UserResult.Success<*> -> {
                    val users = it.data as MutableList<ItemsItem>
                    if (users.isEmpty()) {
                        showNotFound(true)
                    } else {
                        showNotFound(false)
                        adapter.setData(users)
                    }
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

        settingViewModel.getThemeSettings().observe(this){
            if(it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchMode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchMode.isChecked = false
            }
        }

        binding.switchMode.setOnCheckedChangeListener { _, isChecked ->
            settingViewModel.saveTheme(isChecked)
        }
    }
    private fun showNotFound(isDataNotFound: Boolean) {
        binding.apply {
            if (isDataNotFound) {
                rvUsers.visibility = View.GONE
                ivNotfound.visibility = View.VISIBLE
            } else {
                rvUsers.visibility = View.VISIBLE
                ivNotfound.visibility = View.GONE
            }
        }
    }
}