package com.habit.tracker.data.repository

import com.habit.tracker.data.mapper.OrganizationResponseMapper
import com.habit.tracker.data.mapper.ProfileResponseMapper
import com.habit.tracker.data.mapper.RequestResponseMapper
import com.habit.tracker.data.source.remote.api.RequestsApi
import com.habit.tracker.domain.entity.Organization
import com.habit.tracker.domain.entity.Profile
import com.habit.tracker.domain.entity.Request
import com.habit.tracker.domain.repository.RequestRepository
import javax.inject.Inject

class RequestRepositoryImpl @Inject constructor(
    private val remoteDataSource: RequestsApi,
    private val organizationsMapper: OrganizationResponseMapper,
    private val requestsMapper: RequestResponseMapper,
    private val profileMapper: ProfileResponseMapper,
) : RequestRepository {

    override suspend fun getOrganizations(): List<Organization> {
        val result = remoteDataSource.fetchOrganizations()
        return organizationsMapper.map(result)
    }

    override suspend fun getOrganizationById(organizationId: Int): Organization {
        val result = remoteDataSource.fetchOrganizationById(organizationId)
        return organizationsMapper.map(result)
    }

    override suspend fun getOrganizationRequests(organizationId: Int): List<Request> {
        val result = remoteDataSource.fetchOrganizationRequests(organizationId)
        return requestsMapper.map(result)
    }

    override suspend fun getOrganizationRequestById(organizationId: Int, requestId: Int): Request {
        val result = remoteDataSource.fetchOrganizationRequestById(organizationId, requestId)
        return requestsMapper.map(result)
    }

    override suspend fun fetchProfile(): Profile {
        val result = remoteDataSource.fetchProfile().result
        return profileMapper.map(result)
    }
}