package com.habit.tracker.data.source.remote.model.response

import com.google.gson.annotations.SerializedName
import com.habit.tracker.data.source.remote.model.dto.OrganizationDTO

class OrganizationListResponse(
    @SerializedName("results")
    val results: List<OrganizationDTO>
)