package com.bangkit.githubusers2.UI.Detail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.githubusers2.Adapter.SectionsPagerAdapter
import com.bangkit.githubusers2.Model.DetailUsersViewModel
import com.bangkit.githubusers2.R
import com.bangkit.githubusers2.UI.Theme.ThemeActivity
import com.bangkit.githubusers2.databinding.ActivityDetailUsersBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUsersBinding
    private val detailUserViewModel by viewModels<DetailUsersViewModel>()

    companion object {
        const val USERSNAME_USERS = "username_users"
        const val KEY_ID = "id_users"
        const val KEY_AVATAR = "avatar_users"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Users"

        binding.progressBarDetail.setProgressTintList(ColorStateList.valueOf(Color.RED));
        binding.llDetail.visibility = View.GONE
        binding.tabLayout.visibility = View.GONE
        binding.viewPager.visibility = View.GONE
        binding.toggleFav.visibility = View.GONE

        val username = intent.getStringExtra(USERSNAME_USERS)
        val id = intent.getIntExtra(KEY_ID, 0)
        val avatar = intent.getStringExtra(KEY_AVATAR)
        val bundle = Bundle()
        bundle.putString(USERSNAME_USERS, username)


        detailUserViewModel.usersDetail(username.toString())
        detailUserViewModel.getUsersDetail().observe(this) {
            showLoading(false)
            if (it != null)
                binding.apply {
                    txtNama.text = it.name
                    txtUsername.text = it.login
                    countRepository.text = it.public_repos.toString()
                    countFollower.text = it.followers.toString()
                    countFollowing.text = it.following.toString()
                    company.text = "Company: ${it.company}"
                    location.text = "Location: ${it.location}"

                    Glide.with(this@DetailUsersActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .circleCrop()
                        .into(binding.imgDetailUsers)
                }
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailUserViewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFav.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFav.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFav.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                detailUserViewModel.addFav(username.toString(), id, avatar.toString())
            } else {
                detailUserViewModel.removeFav(id)
            }
            binding.toggleFav.isChecked = _isChecked
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailUserViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }

    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.llDetail.visibility = View.GONE
            binding.tabLayout.visibility = View.GONE
            binding.viewPager.visibility = View.GONE
            binding.toggleFav.visibility = View.GONE
            binding.progressBarDetail.visibility = View.VISIBLE
        } else {
            binding.llDetail.visibility = View.VISIBLE
            binding.tabLayout.visibility = View.VISIBLE
            binding.viewPager.visibility = View.VISIBLE
            binding.toggleFav.visibility = View.VISIBLE
            binding.progressBarDetail.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        val item: MenuItem = menu!!.findItem(R.id.favorite)
        item.setVisible(false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(applicationContext, ThemeActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return false
        }
    }
}