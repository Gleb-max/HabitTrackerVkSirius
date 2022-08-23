package com.habit.tracker.data.source.remote.model.request

import com.google.gson.annotations.SerializedName

class AuthRequest(
    @SerializedName("phone")
    val phone: String,
)