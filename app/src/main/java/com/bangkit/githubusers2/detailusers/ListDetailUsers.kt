package com.bangkit.githubusers2.detailusers

data class ListDetailUsers(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val name: String,
    val company: String,
    val location: String,
    val followers: Int,
    val followers_url: String,
    val following: Int,
    val following_url: String,
    val public_repos: Int,
    val repos_url: String
)