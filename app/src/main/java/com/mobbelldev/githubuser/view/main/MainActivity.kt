package com.mobbelldev.githubuser.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.mobbelldev.githubuser.R
import com.mobbelldev.githubuser.core.helper.IOnItemClickCallback
import com.mobbelldev.githubuser.core.helper.ShowLoading.showLoading
import com.mobbelldev.githubuser.data.remote.response.ItemsItem
import com.mobbelldev.githubuser.databinding.ActivityMainBinding
import com.mobbelldev.githubuser.view.adapter.GithubUserAdapter
import com.mobbelldev.githubuser.view.detail.DetailUserActivity
import com.mobbelldev.githubuser.view.favorite.FavoriteActivity
import com.mobbelldev.githubuser.view.main.viewmodel.MainViewModel
import com.mobbelldev.githubuser.view.theme.ThemeActivity
import com.mobbelldev.githubuser.view.theme.viewmodel.ThemeViewModel
import com.mobbelldev.githubuser.view.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModels()
    private val themeViewModel by viewModels<ThemeViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                resources.getColor(R.color.dark_brown, resources.newTheme()),
                resources.getColor(R.color.dark_brown, resources.newTheme())
            )
        )
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupMenuAppBar()

        setupThemeSetting()

        mainViewModel.getSearchUsers()

        setupSearchBar()

        setupAdapter()

        mainViewModel.isLoading.observe(this) {
            showLoading(
                binding.progressBar,
                it
            )
        }

    }

    private fun setupThemeSetting() {
        themeViewModel.getThemeSetting().observe(this@MainActivity) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setupMenuAppBar() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.title_favorite -> {
                    startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
                    true
                }

                R.id.title_theme -> {
                    startActivity(Intent(this@MainActivity, ThemeActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }

    private fun setupSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val q = searchView.text
                    searchView.hide()
                    mainViewModel.getSearchUsers(q.toString())
                    false
                }
        }
    }

    private fun setupAdapter() {
        val layoutManager = GridLayoutManager(this@MainActivity, 2)
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.setHasFixedSize(true)

        val githubUserAdapter = GithubUserAdapter()
        mainViewModel.searchUser.observe(this) {
            githubUserAdapter.submitList(it)
        }
        binding.rvUsers.adapter = githubUserAdapter

        githubUserAdapter.setOnItemClickCallback(object : IOnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val bundle = Bundle()
                bundle.putString(DetailUserActivity.DETAIL_USER, data.login)
                intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        })

    }
}