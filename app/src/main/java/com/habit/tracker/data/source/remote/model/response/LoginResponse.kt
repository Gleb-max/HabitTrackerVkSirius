package com.habit.tracker.data.source.remote.model.response

import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("result")
    val result: String,

    @SerializedName("token")
    val token: String?
)