package com.dicoding.storyapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.storyapp.data.remote.response.ListStoryItem
import com.dicoding.storyapp.databinding.RowUserBinding
import com.dicoding.storyapp.ui.activity.DetailActivity
import com.dicoding.storyapp.utils.DateFormatter
import java.util.*

class RemoteAdapter :
    PagingDataAdapter<ListStoryItem, RemoteAdapter.ApiViewHolder>(DIFF_CALLBACK){

    override fun onBindViewHolder(holder: ApiViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class ApiViewHolder(private val binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            binding.listName.text = data.name
            Glide.with(binding.listStory.context)
                .load(data.photoUrl)
                .circleCrop()
                .into(binding.listStory)
            binding.listCreated.text = DateFormatter.formatDate(data.createdAt, TimeZone.getDefault().id)
            itemView.setOnClickListener {
                val toDetail = Intent(itemView.context, DetailActivity::class.java)
                toDetail.putExtra(DetailActivity.LIST_STORY, data)
                itemView.context.startActivity(toDetail)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : ApiViewHolder {
        val binding = RowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ApiViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}