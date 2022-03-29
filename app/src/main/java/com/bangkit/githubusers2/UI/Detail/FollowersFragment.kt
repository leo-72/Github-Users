package com.bangkit.githubusers2.UI.Detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubusers2.Adapter.UsersAdapter
import com.bangkit.githubusers2.Model.FollowersViewModel
import com.bangkit.githubusers2.R
import com.bangkit.githubusers2.databinding.FragmentFollowersBinding
import com.bangkit.githubusers2.searchusers.ListUsers


class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private val followersViewModel by viewModels<FollowersViewModel>()
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(DetailUsersActivity.USERSNAME_USERS).toString()
        _binding = FragmentFollowersBinding.bind(view)

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

        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.apply {
            rvListFollowers.setHasFixedSize(true)
            rvListFollowers.layoutManager = LinearLayoutManager(activity)
            rvListFollowers.adapter = usersAdapter
        }

        followersViewModel.setListFollowers(username)
        followersViewModel.getListFollowers().observe(viewLifecycleOwner) {
            showLoading(false)
            if (it != null) {
                usersAdapter.setList(it)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.rvListFollowers.visibility = View.GONE
            binding.progressBarFollowers.visibility = View.VISIBLE
        } else {
            binding.rvListFollowers.visibility = View.VISIBLE
            binding.progressBarFollowers.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}