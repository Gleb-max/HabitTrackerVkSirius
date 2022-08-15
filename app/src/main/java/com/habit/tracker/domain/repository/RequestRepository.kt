package com.habit.tracker.domain.repository

//todo
interface RequestRepository {

    suspend fun getOrganizations()

    suspend fun getOrganizationById(organizationId: Int)

    suspend fun getOrganizationRequests(organizationId: Int)
}