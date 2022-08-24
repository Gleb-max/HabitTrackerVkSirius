package com.habit.tracker.data.source.remote.api

import com.habit.tracker.data.source.remote.model.request.ReqRequest
import com.habit.tracker.data.source.remote.model.response.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface RequestsApi {

    @GET("profile")
    suspend fun fetchProfile(): ProfileResponse

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

    @Multipart
    @POST("create_request")
    suspend fun createRequest(
        @Part("info") request: ReqRequest,
        @Part photos: List<MultipartBody.Part>,
    )
}