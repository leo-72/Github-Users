package com.bangkit.githubusers2.api

import com.bangkit.githubusers2.detailusers.ListDetailUsers
import com.bangkit.githubusers2.searchusers.ListUsers
import com.bangkit.githubusers2.searchusers.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUsers(
        @Query("q") query: String
    ): Call<UsersResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun detailUsers(
        @Path("username") username: String
    ): Call<ListDetailUsers>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun followersUsers(
        @Path("username") username: String
    ): Call<ArrayList<ListUsers>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun followingsUsers(
        @Path("username") username: String
    ): Call<ArrayList<ListUsers>>
}