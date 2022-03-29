package com.bangkit.githubusers2.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.githubusers2.Event
import com.bangkit.githubusers2.api.ApiConfig
import com.bangkit.githubusers2.searchusers.ListUsers
import com.bangkit.githubusers2.searchusers.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val _users = MutableLiveData<ArrayList<ListUsers>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun searchUsers(query: String) {
        _isLoading.value = true
        ApiConfig.getApiService()
            .searchUsers(query)
            .enqueue(object : Callback<UsersResponse> {
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful)
                        _users.value = response.body()?.items
                    else {
                        Log.e(TAG, "Gagal")
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "Gagal")
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<ListUsers>> {
        return _users
    }
}