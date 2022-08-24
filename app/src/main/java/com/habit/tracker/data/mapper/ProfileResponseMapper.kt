package com.habit.tracker.data.mapper

import com.habit.tracker.data.source.remote.model.dto.ProfileDTO
import com.habit.tracker.domain.entity.Profile
import javax.inject.Inject

class ProfileResponseMapper @Inject constructor() {

    fun map(data: ProfileDTO) = Profile(
        id = data.id,
        name = data.name,
        phone = data.phone,
        image = data.image,
    )
}