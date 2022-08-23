package com.habit.tracker.data.source.remote.model.request

class LoginRequest(
    val phone: String,
    val code: Int,
) {
}