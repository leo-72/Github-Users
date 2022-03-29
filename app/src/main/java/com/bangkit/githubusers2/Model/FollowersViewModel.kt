package com.bangkit.githubusers2.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.githubusers2.api.ApiConfig
import com.bangkit.githubusers2.searchusers.ListUsers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val _listFollowers = MutableLiveData<ArrayList<ListUsers>>()

    companion object {
        private const val TAG = "FollowersViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setListFollowers(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService()
            .followersUsers(username)
            .enqueue(object : Callback<ArrayList<ListUsers>> {
                override fun onResponse(
                    call: Call<ArrayList<ListUsers>>,
                    response: Response<ArrayList<ListUsers>>
                ) {
                    if (response.isSuccessful)
                        _isLoading.value = false
                        _listFollowers.postValue(response.body())
                }

                override fun onFailure(call: Call<ArrayList<ListUsers>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<ListUsers>> {
        return _listFollowers
    }
}