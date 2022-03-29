package com.bangkit.githubusers2.Model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.githubusers2.Event
import com.bangkit.githubusers2.api.ApiConfig
import com.bangkit.githubusers2.detailusers.ListDetailUsers
import com.bangkit.githubusers2.favuser.DatabaseUser
import com.bangkit.githubusers2.favuser.FavUser
import com.bangkit.githubusers2.favuser.FavUserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUsersViewModel(application: Application) : AndroidViewModel(application) {
    val _users = MutableLiveData<ListDetailUsers>()

    private var userDao: FavUserDao?
    private var dbUser: DatabaseUser?

    init {
        dbUser = DatabaseUser.getDatabaseUser(application)
        userDao = dbUser?.favUserDao()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    companion object {
        private const val TAG = "DetailUsersViewModel"
    }

    fun usersDetail(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService()
            .detailUsers(username)
            .enqueue(object : Callback<ListDetailUsers> {
                override fun onResponse(
                    call: Call<ListDetailUsers>,
                    response: Response<ListDetailUsers>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful)
                        _users.postValue(response.body())
                }

                override fun onFailure(call: Call<ListDetailUsers>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })
    }


    fun getUsersDetail(): LiveData<ListDetailUsers> {
        return _users
    }

    fun addFav(username:String, id:Int, avatarUrl:String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavUser(
                    username,
                    id,
                    avatarUrl
            )
            userDao?.addToFav(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFav(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFav(id)
        }
    }
}