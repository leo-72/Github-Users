package com.bangkit.githubusers2.Model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bangkit.githubusers2.favuser.DatabaseUser
import com.bangkit.githubusers2.favuser.FavUser
import com.bangkit.githubusers2.favuser.FavUserDao

class FavViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: FavUserDao?
    private var dbUser: DatabaseUser?

    init {
        dbUser = DatabaseUser.getDatabaseUser(application)
        userDao = dbUser?.favUserDao()
    }

    fun getFavUser(): LiveData<List<FavUser>>? {
        return userDao?.getFavUser()
    }
}
