package com.mobbelldev.githubuser.view.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.mobbelldev.githubuser.R
import com.mobbelldev.githubuser.core.helper.IOnItemClickCallback
import com.mobbelldev.githubuser.data.local.entity.FavoriteEntity
import com.mobbelldev.githubuser.data.remote.response.ItemsItem
import com.mobbelldev.githubuser.databinding.ActivityFavoriteBinding
import com.mobbelldev.githubuser.view.adapter.GithubUserAdapter
import com.mobbelldev.githubuser.view.detail.DetailUserActivity
import com.mobbelldev.githubuser.view.favorite.viewmodel.FavoriteViewModel
import com.mobbelldev.githubuser.view.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private val binding: ActivityFavoriteBinding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val favoriteAdapter = GithubUserAdapter()
        binding.progressBar.visibility = View.VISIBLE
        favoriteViewModel.getAllFavoriteUser().observe(this) { users ->
            with(binding) {
                rvUsers.setHasFixedSize(true)
                rvUsers.layoutManager = GridLayoutManager(this@FavoriteActivity, 2)

                if (users != null) {
                    progressBar.visibility = View.GONE
                    favoriteAdapter.submitList(usersList(users))
                    rvUsers.adapter = favoriteAdapter

                    favoriteAdapter.setOnItemClickCallback(object : IOnItemClickCallback {
                        override fun onItemClicked(data: ItemsItem) {
                            val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                            intent.putExtra(DetailUserActivity.DETAIL_USER, data.login)
                            startActivity(intent)
                        }

                    })
                }
            }
        }

    }

    private fun usersList(users: List<FavoriteEntity>): ArrayList<ItemsItem> {
        val items = arrayListOf<ItemsItem>()
        users.map {
            val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
            items.add(item)
        }

        return items
    }

    companion object {
    }
}