package com.bangkit.githubusers2.UI.Favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubusers2.Adapter.UsersAdapter
import com.bangkit.githubusers2.Model.FavViewModel
import com.bangkit.githubusers2.UI.Detail.DetailUsersActivity
import com.bangkit.githubusers2.databinding.ActivityFavoriteBinding
import com.bangkit.githubusers2.favuser.FavUser
import com.bangkit.githubusers2.searchusers.ListUsers

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var usersAdapter: UsersAdapter
    private val favViewModel by viewModels<FavViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"

        usersAdapter = UsersAdapter()
        usersAdapter.notifyDataSetChanged()

        usersAdapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListUsers) {
                intent = Intent(this@FavoriteActivity, DetailUsersActivity::class.java)
                intent.putExtra(DetailUsersActivity.USERSNAME_USERS, data.login)
                intent.putExtra(DetailUsersActivity.KEY_ID, data.id)
                intent.putExtra(DetailUsersActivity.KEY_AVATAR, data.avatar_url)
                startActivity(intent)
            }
        })

        binding.apply {
            binding.rvFavUser.setHasFixedSize(true)
            binding.rvFavUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            binding.rvFavUser.adapter = usersAdapter
        }

        favViewModel.getFavUser()?.observe(this, {
            if (it!=null){
                val list = mapList(it)
                usersAdapter.setList(list)
            }
        })
    }

    private fun mapList(users: List<FavUser>): ArrayList<ListUsers> {
        val listUsers = ArrayList<ListUsers>()
        for (user in users){
            val usersMap = ListUsers(
                user.id,
                user.login,
                user.avatar_url
            )
            listUsers.add(usersMap)
        }
        return listUsers
    }
}