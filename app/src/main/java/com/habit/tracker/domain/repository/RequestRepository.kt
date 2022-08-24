package com.habit.tracker.domain.repository

import com.habit.tracker.domain.entity.Organization
import com.habit.tracker.domain.entity.Profile
import com.habit.tracker.domain.entity.Request

interface RequestRepository {

    suspend fun getOrganizations(): List<Organization>

    suspend fun getOrganizationById(organizationId: Int): Organization

    suspend fun getOrganizationRequests(organizationId: Int): List<Request>

    suspend fun getOrganizationRequestById(organizationId: Int, requestId: Int): Request

    suspend fun createRequest(
        organizationId: Int,
        title: String,
        description: String,
        photos: List<String>
    )

    suspend fun fetchProfile(): Profile
}