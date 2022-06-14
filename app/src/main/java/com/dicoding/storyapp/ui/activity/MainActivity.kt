package com.dicoding.storyapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyapp.R
import com.dicoding.storyapp.adapter.LoadingStateAdapter
import com.dicoding.storyapp.adapter.RemoteAdapter
import com.dicoding.storyapp.data.local.TokenPreferences
import com.dicoding.storyapp.data.local.User
import com.dicoding.storyapp.databinding.ActivityMainBinding
import com.dicoding.storyapp.viewmodel.MainViewModel
import com.dicoding.storyapp.viewmodelfactory.RemoteModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var user: User
    private val mainViewModel: MainViewModel by viewModels {
        RemoteModelFactory.getInstanceStory(this)
    }
    private lateinit var mSystemPreferences: TokenPreferences

    override fun onCreateOptionsMenu(item: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, item)

        return super.onCreateOptionsMenu(item)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_story -> {
                val toAdd = Intent(this@MainActivity, AddActivity::class.java)
                startActivity(toAdd)
                true
            }
            R.id.add_maps -> {
                val toMap = Intent(this@MainActivity, MapsActivity::class.java)
                startActivity(toMap)
                true
            }
            R.id.add_logout -> {
                mSystemPreferences.getUser(User(
                    name = null,
                    token = null,
                    isLogin = false
                ))
                val intent = Intent(this@MainActivity, StartActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Logout Success", Toast.LENGTH_SHORT).show()
                finish()
                true
            }
            else -> true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mSystemPreferences = TokenPreferences(this)
        user = mSystemPreferences.getToken()
        binding.recyclerStory.layoutManager = LinearLayoutManager(this)
        setUserStory()
    }

    private fun setUserStory() {
        val remoteAdapter = RemoteAdapter()
        binding.recyclerStory.adapter = remoteAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                remoteAdapter.retry()
            }
        )
        mainViewModel.getStory("Bearer ${user.token}").observe(this) {
            remoteAdapter.submitData(lifecycle, it)
            Log.d("AAAA", it.toString())
        }
    }
}