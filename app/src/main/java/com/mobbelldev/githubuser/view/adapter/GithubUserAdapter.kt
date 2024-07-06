package com.mobbelldev.githubuser.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobbelldev.githubuser.data.remote.response.ItemsItem
import com.mobbelldev.githubuser.databinding.ItemRowUserBinding
import com.mobbelldev.githubuser.core.helper.IOnItemClickCallback

class GithubUserAdapter :
    ListAdapter<ItemsItem, GithubUserAdapter.MyViewHolder>(DIFF_UTIL) {
        private lateinit var onItemClickCallback: IOnItemClickCallback

        fun setOnItemClickCallback(onItemClickCallback: IOnItemClickCallback) {
            this.onItemClickCallback = onItemClickCallback
        }
    inner class MyViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemsItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.avatarUrl)
                    .into(ivAvatar)

                tvAvatar.text = data.login
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemRowUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}