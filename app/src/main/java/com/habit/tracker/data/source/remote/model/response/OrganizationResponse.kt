package com.habit.tracker.data.source.remote.model.response

import com.google.gson.annotations.SerializedName
import com.habit.tracker.data.source.remote.model.dto.OrganizationDTO

class OrganizationResponse(
    @SerializedName("result")
    val result: OrganizationDTO
)