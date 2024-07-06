package com.mobbelldev.githubuser.view.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mobbelldev.githubuser.data.remote.response.ItemsItem
import com.mobbelldev.githubuser.databinding.FragmentFollowerAndFollowingBinding
import com.mobbelldev.githubuser.core.helper.IOnItemClickCallback
import com.mobbelldev.githubuser.core.helper.ShowLoading.showLoading
import com.mobbelldev.githubuser.view.adapter.GithubUserAdapter
import com.mobbelldev.githubuser.view.detail.viewmodel.FollowersAndFollowingViewModel

class FollowerAndFollowingFragment : Fragment() {

    private var _binding: FragmentFollowerAndFollowingBinding? = null
    private val binding get() = _binding!!

    private val followerAndFollowingViewModel: FollowersAndFollowingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerAndFollowingBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        followerAndFollowingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(
                binding.progressBar,
                it
            )
        }
    }

    private fun setupAdapter() {
        var position = 1
        var username: String? = null
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1) {
            followerAndFollowingViewModel.getFollowers(username.toString())
        } else {
            followerAndFollowingViewModel.getFollowing(username.toString())
        }
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.setHasFixedSize(true)

        val githubUserAdapter = GithubUserAdapter()
        followerAndFollowingViewModel.apply {
            followers.observe(viewLifecycleOwner) {
                githubUserAdapter.submitList(it)
            }

            following.observe(viewLifecycleOwner) {
                githubUserAdapter.submitList(it)
            }
        }
        binding.rvUsers.adapter = githubUserAdapter

        githubUserAdapter.setOnItemClickCallback(object : IOnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val bundle = Bundle()
                bundle.putString(DetailUserActivity.DETAIL_USER, data.login)
                activity?.intent?.apply {
                    Intent(requireContext(), DetailUserActivity::class.java)
                    putExtras(bundle)
                    startActivity(this)
                }
            }

        })
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }
}