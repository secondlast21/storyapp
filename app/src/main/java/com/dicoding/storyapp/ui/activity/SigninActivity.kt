package com.dicoding.storyapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.storyapp.data.local.TokenPreferences
import com.dicoding.storyapp.data.local.User
import com.dicoding.storyapp.databinding.ActivitySigninBinding
import com.dicoding.storyapp.viewmodel.SigninViewModel
import com.dicoding.storyapp.viewmodelfactory.ViewModelFactory

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var mSystemPreferences: TokenPreferences

    private val signinViewModel: SigninViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.progressBarSignin.visibility = View.INVISIBLE
        mSystemPreferences = TokenPreferences(this)

        binding.signinButton.setOnClickListener {
            val email = binding.myEditEmail.text.toString()
            val password = binding.myEditPassword.text.toString()

            signinViewModel.signIn(email, password).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is com.dicoding.storyapp.data.remote.Result.Loading -> {
                            binding.progressBarSignin.visibility = View.VISIBLE
                        }
                        is com.dicoding.storyapp.data.remote.Result.Error -> {
                            binding.progressBarSignin.visibility = View.INVISIBLE
                            Toast.makeText(this, "Failed. Cause: ${result.error}", Toast.LENGTH_SHORT).show()
                        }
                        is com.dicoding.storyapp.data.remote.Result.Success -> {
                            binding.progressBarSignin.visibility = View.INVISIBLE
                            mSystemPreferences.getUser(User(
                                name = result.data.name,
                                token = result.data.token,
                                isLogin = true
                            ))
                            Toast.makeText(this,"Login Success", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SigninActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }
}