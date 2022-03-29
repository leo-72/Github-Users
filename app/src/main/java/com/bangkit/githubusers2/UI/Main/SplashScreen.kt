package com.bangkit.githubusers2.UI.Main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bangkit.githubusers2.Helper.PrefHelper
import com.bangkit.githubusers2.R
import com.bangkit.githubusers2.databinding.ActivityMainBinding
import com.bangkit.githubusers2.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val pref by lazy { PrefHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when(pref.getBoolean("pref_dark_mode")){
            true -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim)
        val botAnim = AnimationUtils.loadAnimation(this, R.anim.bot_anim)
        binding.logo.startAnimation(topAnim)
        binding.nameApp.startAnimation(botAnim)

        Handler().postDelayed({
            val intent = Intent(this, LoginPageActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}