package com.habit.tracker.data.source.remote.api

import com.habit.tracker.data.source.remote.model.request.AuthRequest
import com.habit.tracker.data.source.remote.model.request.LoginRequest
import com.habit.tracker.data.source.remote.model.request.RegRequest
import com.habit.tracker.data.source.remote.model.response.AuthResponse
import com.habit.tracker.data.source.remote.model.response.LoginResponse
import com.habit.tracker.data.source.remote.model.response.RegResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth")
    suspend fun auth(@Body authRequest: AuthRequest): AuthResponse

    @POST("reg")
    suspend fun register(@Body regRequest: RegRequest): RegResponse

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}