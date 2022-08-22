package com.habit.tracker.domain.entity

data class User(
    val id: String? = null,
    val name: String,
    val phone: String,
) {

    companion object {

        fun emptyUser() = User(null, "", "")
    }
}