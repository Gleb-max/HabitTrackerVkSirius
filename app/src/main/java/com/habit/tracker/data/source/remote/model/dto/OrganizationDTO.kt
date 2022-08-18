package com.habit.tracker.data.source.remote.model.dto

import com.google.gson.annotations.SerializedName

class OrganizationDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("geo")
    val geo: GeoDTO,
)