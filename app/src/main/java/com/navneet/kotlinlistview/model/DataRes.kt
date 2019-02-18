package com.navneet.kotlinlistview.model
import com.google.gson.annotations.SerializedName


data class DataRes(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("success")
    val success: Int
)

data class Data(
    @SerializedName("chat")
    val chat: Int,
    @SerializedName("chattype")
    val chattype: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("durationtype")
    val durationtype: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isfree")
    val isfree: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int
)