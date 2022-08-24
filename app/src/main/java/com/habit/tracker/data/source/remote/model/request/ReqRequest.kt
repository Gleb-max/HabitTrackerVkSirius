package com.habit.tracker.data.source.remote.model.request

import com.google.gson.annotations.SerializedName

class ReqRequest(
    @SerializedName("organizationId")
    val organizationId: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,
)