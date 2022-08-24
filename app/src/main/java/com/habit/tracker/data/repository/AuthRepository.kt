package com.habit.tracker.data.repository

import com.habit.tracker.data.source.local.UserPreferences
import com.habit.tracker.data.source.remote.api.AuthApi
import com.habit.tracker.data.source.remote.model.request.AuthRequest
import com.habit.tracker.data.source.remote.model.request.LoginRequest
import com.habit.tracker.data.source.remote.model.request.RegRequest
import com.habit.tracker.domain.entity.User
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthApi,
    private val localDataSource: UserPreferences,
) {

    fun isLoggedIn() = localDataSource.getUser().token != null

    suspend fun auth(phone: String): String {
        return remoteDataSource.auth(AuthRequest(phone)).result
    }

    suspend fun reg(phone: String, name: String): String {
        return remoteDataSource.register(RegRequest(phone, name)).result
    }

    suspend fun login(phone: String, code: String): String? {
        val token = remoteDataSource.login(LoginRequest(phone, code)).token
        if (token != null) {
            val user = User(token = token)
            localDataSource.saveUser(user)
        }
        return token
    }
}