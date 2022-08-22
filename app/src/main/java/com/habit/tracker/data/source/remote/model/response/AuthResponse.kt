package com.habit.tracker.data.source.remote.model.response

import com.google.gson.annotations.SerializedName
import com.habit.tracker.data.source.local.model.AuthResult

class AuthResponse(
    @SerializedName("result")
    val result: String
)