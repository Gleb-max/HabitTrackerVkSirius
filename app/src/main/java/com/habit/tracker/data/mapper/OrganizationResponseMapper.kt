package com.habit.tracker.data.mapper

import com.habit.tracker.data.source.remote.model.dto.OrganizationDTO
import com.habit.tracker.data.source.remote.model.response.OrganizationListResponse
import com.habit.tracker.data.source.remote.model.response.OrganizationResponse
import com.habit.tracker.domain.entity.Geo
import com.habit.tracker.domain.entity.Organization
import javax.inject.Inject

class OrganizationResponseMapper @Inject constructor() {

    private fun map(data: OrganizationDTO): Organization {
        val geo = Geo(
            lat = data.geo.latitude,
            long = data.geo.longitude,
        )
        return Organization(
            id = data.id,
            name = data.name,
            address = data.address,
            geo = geo,
        )
    }

    fun map(data: OrganizationListResponse): List<Organization> {
        return data.results.map { map(it) }
    }

    fun map(data: OrganizationResponse): Organization {
        return map(data.result)
    }
}