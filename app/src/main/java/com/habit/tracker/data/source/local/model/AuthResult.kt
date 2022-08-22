package com.habit.tracker.data.source.local.model

import com.google.gson.annotations.SerializedName

enum class AuthResult {

    @SerializedName("code")
    CODE,

    @SerializedName("reg")
    REG
}