package com.dicoding.storyapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.storyapp.data.remote.response.ListStoryItem
import com.dicoding.storyapp.databinding.ActivityDetailBinding
import com.dicoding.storyapp.utils.DateFormatter
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var listStory : ListStoryItem
    private lateinit var binding : ActivityDetailBinding

    private fun setStory() {
        Glide.with(this)
            .load(listStory.photoUrl)
            .circleCrop()
            .into(binding.detailPhoto)
        binding.detailName.text = listStory.name
        binding.detailDate.text = DateFormatter.formatDate(listStory.createdAt, TimeZone.getDefault().id)
        binding.detailDesc.text = listStory.description
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val story = intent.getParcelableExtra<ListStoryItem>(LIST_STORY)
        if (story != null) {
            listStory = story
        }
        setStory()
    }

    companion object {
        const val LIST_STORY = "list_story"
    }
}