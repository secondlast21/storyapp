package com.dicoding.storyapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.storyapp.databinding.ActivitySignupBinding
import com.dicoding.storyapp.viewmodel.SignupViewModel
import com.dicoding.storyapp.viewmodelfactory.ViewModelFactory

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private val signupViewModel: SignupViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.progressBarSignup.visibility = View.INVISIBLE

        binding.signupButton.setOnClickListener {
            val name = binding.myEditText.text.toString()
            val email = binding.myEditEmail.text.toString()
            val password = binding.myEditPassword.text.toString()

            signupViewModel.signUp(name, email, password).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is com.dicoding.storyapp.data.remote.Result.Loading -> {
                            binding.progressBarSignup.visibility = View.VISIBLE
                        }
                        is com.dicoding.storyapp.data.remote.Result.Error -> {
                            binding.progressBarSignup.visibility = View.INVISIBLE
                            Toast.makeText(this, "Failed. Cause: ${result.error}", Toast.LENGTH_SHORT).show()
                        }
                        is com.dicoding.storyapp.data.remote.Result.Success -> {
                            binding.progressBarSignup.visibility = View.INVISIBLE
                            Toast.makeText(this, result.data, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SignupActivity, StartActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}