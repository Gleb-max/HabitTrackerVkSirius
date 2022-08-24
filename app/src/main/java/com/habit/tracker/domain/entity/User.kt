package com.habit.tracker.domain.entity

data class User(
    val id: String? = null,
    val token: String? = null,
) {

    companion object {

        fun emptyUser() = User(null, null)
    }
}