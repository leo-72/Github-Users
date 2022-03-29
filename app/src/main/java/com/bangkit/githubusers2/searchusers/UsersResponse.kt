package com.bangkit.githubusers2.searchusers

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @field:SerializedName("items")
    val items: ArrayList<ListUsers>
)
