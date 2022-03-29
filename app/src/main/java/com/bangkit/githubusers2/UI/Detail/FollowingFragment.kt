package com.bangkit.githubusers2.UI.Detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubusers2.Adapter.UsersAdapter
import com.bangkit.githubusers2.Model.FollowingViewModel
import com.bangkit.githubusers2.R
import com.bangkit.githubusers2.databinding.FragmentFollowingBinding
import com.bangkit.githubusers2.searchusers.ListUsers

class FollowingFragment : Fragment(R.layout.fragment_following) {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val followingViewModel by viewModels<FollowingViewModel>()
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(DetailUsersActivity.USERSNAME_USERS).toString()
        _binding = FragmentFollowingBinding.bind(view)

        usersAdapter = UsersAdapter()
        usersAdapter.notifyDataSetChanged()

        usersAdapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListUsers) {
                activity?.let {
                    val intent = Intent(it, DetailUsersActivity::class.java)
                    intent.putExtra(DetailUsersActivity.USERSNAME_USERS, data.login)
                    intent.putExtra(DetailUsersActivity.KEY_ID, data.id)
                    intent.putExtra(DetailUsersActivity.KEY_AVATAR, data.avatar_url)
                    it.startActivity(intent)
                }
            }
        })

        binding.apply {
            rvListFollowing.layoutManager = LinearLayoutManager(context)
            rvListFollowing.setHasFixedSize(true)
            rvListFollowing.adapter = usersAdapter
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followingViewModel.setListFollowing(username)
        followingViewModel.getListFollowing().observe(viewLifecycleOwner) {
            showLoading(false)
            if (it != null) {
                usersAdapter.setList(it)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.rvListFollowing.visibility = View.GONE
            binding.progressBarFollowing.visibility = View.VISIBLE
        } else {
            binding.rvListFollowing.visibility = View.VISIBLE
            binding.progressBarFollowing.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}