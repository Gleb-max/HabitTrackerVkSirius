package com.habit.tracker.data.source.remote.model.response

import com.google.gson.annotations.SerializedName
import com.habit.tracker.data.source.remote.model.dto.RequestDTO

class RequestResponse(
    @SerializedName("result")
    val result: RequestDTO
)