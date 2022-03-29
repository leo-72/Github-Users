package com.bangkit.githubusers2.Adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bangkit.githubusers2.R
import com.bangkit.githubusers2.UI.Detail.FollowersFragment
import com.bangkit.githubusers2.UI.Detail.FollowingFragment
import java.lang.reflect.Array

class SectionsPagerAdapter(private val context: Context, frag: FragmentManager, bundle: Bundle) :
    FragmentPagerAdapter(frag, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragBundle: Bundle

    init {
        fragBundle = bundle
    }

    @StringRes
    private val Tab_Titles = intArrayOf(
        R.string.txt_followers,
        R.string.txt_following
    )

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        if (position == 0){

        }
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(Tab_Titles[position])
    }

}