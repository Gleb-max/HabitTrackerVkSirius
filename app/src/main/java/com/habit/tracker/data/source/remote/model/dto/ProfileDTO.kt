package com.habit.tracker.data.source.remote.model.dto

import com.google.gson.annotations.SerializedName

class ProfileDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("image")
    val image: String,
)