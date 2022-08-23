package com.habit.tracker.data.source.remote.model.request

import com.google.gson.annotations.SerializedName

class RegRequest(
    @SerializedName("phone")
    val phone: String,

    @SerializedName("name")
    val name: String,
)