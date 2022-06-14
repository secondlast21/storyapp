package com.dicoding.storyapp.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dicoding.storyapp.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.signupButton.setOnClickListener {
            val intent = Intent(this@StartActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.signinButton.setOnClickListener {
            val intent = Intent(this@StartActivity, SigninActivity::class.java)
            startActivity(intent)
        }

        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.coverSignin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val titleStart = ObjectAnimator.ofFloat(binding.welcomeTitle, View.ALPHA, 1f).setDuration(500)
        val subtitleStart = ObjectAnimator.ofFloat(binding.welcomeSubtitle, View.ALPHA, 1f).setDuration(500)
        val signin = ObjectAnimator.ofFloat(binding.signinButton, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(titleStart, subtitleStart, signin, signup)
            start()
        }
    }
}