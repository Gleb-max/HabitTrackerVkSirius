package com.habit.tracker.data.source.remote.api

import retrofit2.http.*

interface RequestsApi {

    //todo: add routes

    @GET("list")
    suspend fun fetchOrganizations()
}