package com.mobbelldev.githubuser.view.theme

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobbelldev.githubuser.R
import com.mobbelldev.githubuser.databinding.ActivityThemeBinding
import com.mobbelldev.githubuser.view.theme.viewmodel.ThemeViewModel
import com.mobbelldev.githubuser.view.viewmodel.ViewModelFactory

class ThemeActivity : AppCompatActivity() {
    private val binding: ActivityThemeBinding by lazy {
        ActivityThemeBinding.inflate(layoutInflater)
    }

    private val themeViewModel by viewModels<ThemeViewModel> {
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

        with(binding) {
            themeViewModel.getThemeSetting().observe(this@ThemeActivity) {
                if (it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            }
            switchTheme.setOnCheckedChangeListener { _, isChecked ->
                themeViewModel.setThemeSetting(isChecked)
            }
        }
    }
}