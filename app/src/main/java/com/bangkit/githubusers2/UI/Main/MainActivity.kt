package com.bangkit.githubusers2.UI.Main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubusers2.Adapter.UsersAdapter
import com.bangkit.githubusers2.Model.MainViewModel
import com.bangkit.githubusers2.R
import com.bangkit.githubusers2.UI.Theme.ThemeActivity
import com.bangkit.githubusers2.UI.Detail.DetailUsersActivity
import com.bangkit.githubusers2.UI.Favorite.FavoriteActivity
import com.bangkit.githubusers2.databinding.ActivityMainBinding
import com.bangkit.githubusers2.searchusers.ListUsers
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersAdapter: UsersAdapter
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usersAdapter = UsersAdapter()
        usersAdapter.notifyDataSetChanged()

        usersAdapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListUsers) {
                intent = Intent(this@MainActivity, DetailUsersActivity::class.java)
                intent.putExtra(DetailUsersActivity.USERSNAME_USERS, data.login)
                intent.putExtra(DetailUsersActivity.KEY_ID, data.id)
                intent.putExtra(DetailUsersActivity.KEY_AVATAR, data.avatar_url)
                startActivity(intent)
            }
        })

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = usersAdapter

            binding.searchUsername.addTextChangedListener(textWatcher)
        }

        mainViewModel.getSearchUsers().observe(this) {
            if (it != null) {
                usersAdapter.setList(it)
                showLoading(false)
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            searchUsers()
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    private fun searchUsers() {
        binding.apply {
            val query = searchUsername.text.toString()
                if (query.isEmpty()) {
                    binding.searchUsername.error = "User not Found"
                }
            showLoading(true)
            mainViewModel.searchUsers(query)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.recyclerView.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(applicationContext, ThemeActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.favorite -> {
                val intent2 = Intent(applicationContext, FavoriteActivity::class.java)
                startActivity(intent2)
                return true
            }
            else -> return false
        }
    }
}