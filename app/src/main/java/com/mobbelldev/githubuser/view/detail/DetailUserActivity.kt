package com.mobbelldev.githubuser.view.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.mobbelldev.githubuser.R
import com.mobbelldev.githubuser.core.helper.ShowLoading.showLoading
import com.mobbelldev.githubuser.core.util.Result
import com.mobbelldev.githubuser.data.local.entity.FavoriteEntity
import com.mobbelldev.githubuser.databinding.ActivityDetailUserBinding
import com.mobbelldev.githubuser.view.adapter.SectionPagerAdapter
import com.mobbelldev.githubuser.view.detail.viewmodel.DetailViewModel
import com.mobbelldev.githubuser.view.viewmodel.ViewModelFactory

class DetailUserActivity : AppCompatActivity() {
    private val binding: ActivityDetailUserBinding by lazy {
        ActivityDetailUserBinding.inflate(layoutInflater)
    }

    private val detailViewModel by viewModels<DetailViewModel> {
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

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundle = intent.extras
        val username: String? = bundle?.getString(DETAIL_USER)
        username?.let {
            detailViewModel.getDetailUser(username).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(
                                binding.progressBar,
                                true
                            )
                        }

                        is Result.Success -> {
                            val data = result.data
                            with(binding) {
                                showLoading(
                                    progressBar,
                                    false
                                )
                                Glide
                                    .with(this@DetailUserActivity)
                                    .load(data.avatarUrl)
                                    .into(ivAvatar)

                                materialToolbar.title = data.login
                                materialToolbar.setOnClickListener {
                                    finish()
                                }

                                setupTabLayout(data.login ?: "")

                                detailViewModel.getFavoriteUserByUsername(it)
                                    .observe(this@DetailUserActivity) { dataUser ->
                                        val user = FavoriteEntity(
                                            data.login.toString(),
                                            data.avatarUrl
                                        )

                                        dataUser?.let {
                                            toggleFabButton(true, user)
                                        } ?: run {
                                            toggleFabButton(false, user)
                                        }
                                    }
                            }
                        }

                        is Result.Error -> {
                            showLoading(
                                binding.progressBar,
                                false
                            )

                            Log.e(
                                DetailUserActivity::class.java.simpleName,
                                "message error: ${result.error}"
                            )

                            Toast.makeText(
                                this,
                                "Error: ${result.error}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun toggleFabButton(state: Boolean, user: FavoriteEntity) {
        with(binding) {
            if (state) {
                fabAction.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailUserActivity,
                        R.drawable.round_favorite_24
                    )
                )
            } else {
                fabAction.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailUserActivity,
                        R.drawable.round_favorite_border_24
                    )
                )
            }

            fabAction.setOnClickListener {
                if (state) {
                    Toast.makeText(this@DetailUserActivity, "Deleted", Toast.LENGTH_SHORT).show()
                    detailViewModel.deleteUser(user)
                    toggleFabButton(false, user)
                } else {
                    Toast.makeText(this@DetailUserActivity, "Saved", Toast.LENGTH_SHORT).show()
                    detailViewModel.saveUser(user)
                    toggleFabButton(true, user)
                }
            }
        }
    }

    private fun setupTabLayout(username: String) {
        val sectionPagerAdapter = SectionPagerAdapter(this@DetailUserActivity)
        sectionPagerAdapter.username = username

        with(binding) {
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
        const val DETAIL_USER = "detail_user"
    }
}