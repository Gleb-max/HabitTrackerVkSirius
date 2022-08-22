package com.habit.tracker.data.repository

import com.habit.tracker.data.source.local.UserPreferences
import com.habit.tracker.data.source.local.model.AuthResult
import com.habit.tracker.data.source.local.model.RegResult
import com.habit.tracker.data.source.remote.api.AuthApi
import com.habit.tracker.data.source.remote.model.request.AuthRequest
import com.habit.tracker.data.source.remote.model.request.LoginRequest
import com.habit.tracker.data.source.remote.model.request.RegRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthApi,
    private val localDataSource: UserPreferences,
) {

    fun isLoggedIn() = localDataSource.getUser().id != null

    suspend fun auth(phone: String): String {
        return remoteDataSource.auth(AuthRequest(phone)).result
    }

    suspend fun reg(phone: String, name: String): RegResult {
        return remoteDataSource.register(RegRequest(phone, name)).result
    }

    suspend fun login(phone: String, code: Int): String? {
        return remoteDataSource.login(LoginRequest(phone, code)).token
    }
}