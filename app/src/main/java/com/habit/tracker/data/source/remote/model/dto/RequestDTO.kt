package com.habit.tracker.data.source.remote.model.dto

import com.google.gson.annotations.SerializedName

class RequestDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("done")
    val done: Boolean,
    @SerializedName("photos")
    val photos: List<String>,
)