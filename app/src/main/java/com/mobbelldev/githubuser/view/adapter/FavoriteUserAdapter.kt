package com.mobbelldev.githubuser.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobbelldev.githubuser.core.helper.IOnItemClickCallback
import com.mobbelldev.githubuser.data.remote.response.ItemsItem
import com.mobbelldev.githubuser.databinding.ItemRowUserBinding

class FavoriteUserAdapter : ListAdapter<ArrayList<ItemsItem>, FavoriteUserAdapter.MyViewHolder>(
    DIFF_UTIL
) {
    private lateinit var onItemClickCallback: IOnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: IOnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class MyViewHolder(private var binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArrayList<ItemsItem>) {
            with(binding) {
                data.map {
                    Glide.with(itemView.context)
                        .load(it.avatarUrl)
                        .into(ivAvatar)

                    tvAvatar.text = it.login
                }
//                val user = FavoriteEntity(
//                    data.login.toString(),
//                    data.avatarUrl
//                )
//                Glide.with(itemView.context)
//                    .load(user.avatarUrl)
//                    .into(ivAvatar)
//
//                tvAvatar.text = user.avatarUrl
            }

//            itemView.setOnClickListener {
//                onItemClickCallback.onItemClicked(data)
//            }
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
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ArrayList<ItemsItem>>() {
            override fun areItemsTheSame(
                oldItem: ArrayList<ItemsItem>,
                newItem: ArrayList<ItemsItem>
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ArrayList<ItemsItem>,
                newItem: ArrayList<ItemsItem>
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}