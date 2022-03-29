package com.bangkit.githubusers2.UI.Theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bangkit.githubusers2.Helper.PrefHelper
import com.bangkit.githubusers2.R
import com.bangkit.githubusers2.UI.Detail.DetailUsersActivity
import com.bangkit.githubusers2.databinding.ActivityMainBinding
import com.bangkit.githubusers2.databinding.ActivityThemeBinding

class ThemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeBinding
    private val pref by lazy { PrefHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchMaterial.isChecked = pref.getBoolean("pref_dark_mode")

        binding.switchMaterial.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                pref.put("pref_dark_mode", true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                pref.put("pref_dark_mode", false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}