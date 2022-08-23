package com.habit.tracker.data.source.remote.model.request

import com.google.gson.annotations.SerializedName

class LoginRequest(
    @SerializedName("phone")
    val phone: String,

    @SerializedName("code")
    val code: String,
)