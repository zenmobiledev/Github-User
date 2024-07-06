package com.mobbelldev.githubuser.view.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobbelldev.githubuser.view.detail.FollowerAndFollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowerAndFollowingFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowerAndFollowingFragment.ARG_POSITION, position + 1)
            putString(FollowerAndFollowingFragment.ARG_USERNAME, username)
        }
        return fragment
    }

}