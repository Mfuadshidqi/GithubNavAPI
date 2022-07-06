package com.fuad.fuadgithub.response

import com.google.gson.annotations.SerializedName

data class ResponseUser(

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: List<ItemsUser?>? = null,

    )

data class ItemsUser(

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,


    )
