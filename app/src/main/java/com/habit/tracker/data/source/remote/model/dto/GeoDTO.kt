package com.habit.tracker.data.source.remote.model.dto

import com.google.gson.annotations.SerializedName

class GeoDTO(
    @SerializedName("long")
    val longitude: Float,
    @SerializedName("lat")
    val latitude: Float,
)