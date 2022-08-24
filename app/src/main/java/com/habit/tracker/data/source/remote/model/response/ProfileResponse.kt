package com.habit.tracker.data.source.remote.model.response

import com.google.gson.annotations.SerializedName
import com.habit.tracker.data.source.remote.model.dto.ProfileDTO

class ProfileResponse(
    @SerializedName("result")
    val result: ProfileDTO
)