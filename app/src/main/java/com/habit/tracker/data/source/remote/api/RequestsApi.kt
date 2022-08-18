package com.habit.tracker.data.source.remote.api

import com.habit.tracker.data.source.remote.model.response.OrganizationListResponse
import com.habit.tracker.data.source.remote.model.response.OrganizationResponse
import com.habit.tracker.data.source.remote.model.response.RequestListResponse
import com.habit.tracker.data.source.remote.model.response.RequestResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestsApi {

    //todo: add routes

    //todo: add filters or getting by region
    @GET("organizations")
    suspend fun fetchOrganizations(): OrganizationListResponse

    @GET("organizations/{organization_id}")
    suspend fun fetchOrganizationById(@Path("organization_id") organizationId: Int): OrganizationResponse

    @GET("organizations/{organization_id}/requests")
    suspend fun fetchOrganizationRequests(@Path("organization_id") organizationId: Int): RequestListResponse

    @GET("organizations/{organization_id}/requests/{request_id}")
    suspend fun fetchOrganizationRequestById(
        @Path("organization_id") organizationId: Int,
        @Path("request_id") requestId: Int
    ): RequestResponse
}