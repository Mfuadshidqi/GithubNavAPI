package com.fuad.fuadgithub.service

import com.fuad.fuadgithub.response.ItemsUser
import com.fuad.fuadgithub.response.ResponseDetailUser
import com.fuad.fuadgithub.response.ResponseUser
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
//    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ): Call<ResponseUser>

//    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<ResponseDetailUser>

//    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String?
    ): Call<List<ItemsUser>>

//    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String?
    ): Call<List<ItemsUser>>
}