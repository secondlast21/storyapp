package com.dicoding.storyapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.local.TokenPreferences

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var mSystemPreferences: TokenPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        mSystemPreferences = TokenPreferences(this)
        val login = mSystemPreferences.isLogin()
        Log.d("login", login.toString())

        Handler(Looper.getMainLooper()).postDelayed({
            if (login) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashActivity, StartActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)

    }
}